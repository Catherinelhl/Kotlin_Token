package cn.catherine.token.service

import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.os.Environment
import android.os.IBinder
import android.util.LongSparseArray
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.InstallTool
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.SystemTool
import cn.catherine.token.tool.language.LanguageTool
import java.io.File

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 16:06
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.service
+--------------+---------------------------------
+ description  +   服務：開啟一個在应用内監聽更新APP下載進度的服務
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class DownloadService : Service() {
    private val TAG = DownloadService::class.java.simpleName
    //管理下载
    private var mDownloadManager: DownloadManager? = null
    private val mBinder by lazy { DownloadBinder() }
    private var mApkPaths: LongSparseArray<String>? = null
    private var downloadFinishReceiver: DownloadFinishReceiver? = null

    override fun onBind(intent: Intent?): IBinder? = mBinder

    override fun onCreate() {
        super.onCreate()
        mDownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        mApkPaths = LongSparseArray()
        //注册下载完成的广播
        downloadFinishReceiver = DownloadFinishReceiver()
        registerReceiver(downloadFinishReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onDestroy() {
        unregisterReceiver(downloadFinishReceiver)//取消注册广播接收者
        super.onDestroy()
    }

    inner class DownloadBinder : Binder() {

        /**
         * 下载
         *
         * @param apkUrl 下载的url
         */
        fun startDownload(apkUrl: String): Long {
            //删除原有的APK
            clearApk(this@DownloadService, Constants.ValueMaps.DOWNLOAD_APK_NAME)
            //使用DownLoadManager来下载
            val request = DownloadManager.Request(Uri.parse(apkUrl))
            //将文件下载到自己的Download文件夹下,必须是External的
            //这是DownloadManager的限制
            val file = File(
                getExternalFilesDir(Constants.ValueMaps.BCAAS_FILE_DIR)!!.absolutePath,
                Constants.ValueMaps.DOWNLOAD_APK_NAME
            )
            request.setDestinationUri(Uri.fromFile(file))

            var downloadId: Long = 0
            //添加请求 开始下载
            mDownloadManager?.let {
                downloadId = it.enqueue(request)
                LogTool.d(TAG, file.absolutePath)
                mApkPaths?.let {
                    it.put(downloadId, file.absolutePath)
                }
            }
            return downloadId
        }

        /**
         * 获取进度信息
         *
         * @param downloadId 要获取下载的id
         * @return 进度信息 max-100
         */
        fun getProgress(downloadId: Long): Int {
            //查询进度
            val query = DownloadManager.Query()
                .setFilterById(downloadId)
            var cursor: Cursor? = null
            var progress = 0
            try {
                cursor = mDownloadManager?.query(query)//获得游标
                if (cursor != null && cursor.moveToFirst()) {
                    //当前的下载量
                    val downloadSoFar =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    //文件总大小
                    val totalBytes =
                        cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                    progress = (downloadSoFar * 1.0f / totalBytes * 100).toInt()
                }
            } finally {
                cursor?.close()
            }

            return progress
        }

    }

    //下载完成的广播
    private inner class DownloadFinishReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            //下载完成的广播接收者
            val completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            var apkPath: String? = mApkPaths?.get(completeDownloadId)
            LogTool.d(TAG, MessageConstants.DOWNLOAD_FINISH_RECEIVER + apkPath!!)
            if (apkPath == null) {
                apkPath =
                    getExternalFilesDir(Constants.ValueMaps.BCAAS_FILE_DIR)!!.absolutePath + Constants.ValueMaps.DOWNLOAD_APK_NAME
            }
            if (!apkPath.isEmpty()) {
                SystemTool.setPermission(apkPath)//提升读写权限,否则可能出现解析异常
                InstallTool.installAndroidAPK(context, apkPath)
            } else {
                LogTool.e(TAG, MessageConstants.APK_PATH_IS_NULL)
            }
        }
    }

    /**
     * 删除之前的apk
     *
     * @param apkName apk名字
     * @return
     */
    fun clearApk(context: Context, apkName: String): File {
        val apkFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), apkName)
        if (apkFile.exists()) {
            apkFile.delete()
        }
        return apkFile
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageTool.setLocal(base))
    }
}