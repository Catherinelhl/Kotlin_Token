package cn.catherine.token.manager

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import cn.catherine.token.R
import cn.catherine.token.base.BaseActivity
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.listener.PermissionListener
import cn.catherine.token.service.DownloadService
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.PermissionTool
import cn.catherine.token.view.dialog.BaseDownloadDialog
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/15 16:14
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.manager
+--------------+---------------------------------
+ description  +    更新版本管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class UpdateVersionManager {

    private val TAG = UpdateVersionManager::class.java.simpleName
    //存储当前需要更新的Android APk路径
    private var updateAndroidAPKURL: String? = null
    //下载版本的服务绑定
    private var mDownloadBinder: DownloadService.DownloadBinder? = null
    //可以取消观察者
    var downloadDisposable: Disposable? = null
    // 下载更新的dialog
    private var baseDownloadDialog: BaseDownloadDialog? = null

    private var activity: Activity? = null

    constructor(activity: Activity?) {
        this.activity = activity
    }

    /**
     * 绑定下载的服务
     */
    fun bindDownloadService() {
        activity?.let {
            val intent = Intent(it, DownloadService::class.java)
            it.startService(intent)
            it.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)//绑定服务
        }

    }


    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mDownloadBinder = service as DownloadService.DownloadBinder
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mDownloadBinder = null
        }
    }

    /**
     * 开始应用开启下载应用更新
     */
    private fun startDownloadAndroidAPk() {
        activity ?: return
        baseDownloadDialog = BaseDownloadDialog().showDialog(activity!!)
        updateAndroidAPKURL ?: return
        LogTool.d(TAG, MessageConstants.START_DOWNLOAD_ANDROID_APK + updateAndroidAPKURL)
        mDownloadBinder ?: return
        val downloadId = mDownloadBinder!!.startDownload(updateAndroidAPKURL!!)
        startCheckProgress(downloadId)

    }

    /**
     * 开始应用内下载
     */
    fun startAppSYNCDownload() {
        activity ?: return
        LogTool.d(TAG, MessageConstants.startAppSYNCDownload)
        //检查Binder不为空的情况，开始检查读写权限
        if (mDownloadBinder != null) {
            PermissionTool.checkWriteStoragePermission(activity!!, object : PermissionListener {
                override fun getPermissionSuccess() {
                    startDownloadAndroidAPk()
                }

                override fun getPermissionFailure() {
                }

            })
        }
    }


    //开始监听进度
    private fun startCheckProgress(downloadId: Long) {
        LogTool.d(TAG, MessageConstants.DOWNLOAD_ID + downloadId)
        Observable
            //无限轮询,准备查询进度,在io线程执行
            .interval(100, 200, TimeUnit.MILLISECONDS, Schedulers.io())
            .filter { aLong -> mDownloadBinder != null }
            .map { aLong -> mDownloadBinder!!.getProgress(downloadId) }//获得下载进度
            .takeUntil { progress -> progress >= 100 }//返回true就停止了,当进度>=100就是下载完成了
            .distinct()//去重复
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onComplete() {
                    //设置进度
                    baseDownloadDialog?.setProgress(100)
                    baseDownloadDialog?.hideDownloadDialog()
                    LogTool.d(TAG, MessageConstants.FINISH_DOWNLOAD)
                }

                override fun onSubscribe(d: Disposable) {
                    downloadDisposable = d

                }

                override fun onNext(progress: Int) {
                    //设置进度
                    baseDownloadDialog?.setProgress(progress)
                }

                override fun onError(throwable: Throwable) {
                    throwable.printStackTrace()
                    activity?.let {
                        if (it is BaseActivity) {
                            it.showToast(it.getString(R.string.install_failed))

                        }

                    }
                }
            })
    }

}