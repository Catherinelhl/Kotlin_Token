package cn.catherine.token.tool

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import java.io.File

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 16:11
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object InstallTool {

    private val TAG = InstallTool::class.java.simpleName

    //普通安装
    fun installAndroidAPK(context: Context, apkPath: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            val file = File(apkPath)
            LogTool.d(TAG, MessageConstants.INSTALL_ANDROID_APK)
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            val apkUri =
                FileProvider.getUriForFile(context, context.packageName + Constants.ValueMaps.FILE_PROVIDER, file)
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(
                Uri.fromFile(File(apkPath)),
                "application/vnd.android.package-archive"
            )
        }
        context.startActivity(intent)
    }
}