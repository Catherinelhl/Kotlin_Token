package cn.catherine.token.tool

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.listener.PermissionListener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 16:14
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool
+--------------+---------------------------------
+ description  +   检查当前APP需要的权限
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object PermissionTool {

    private val TAG = PermissionTool::class.java.simpleName
    //读写权限
    private val PERMISSIONS_STORAGE =
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    //安装权限
    private val PERMISSIONS_INSTALL = arrayOf(Manifest.permission.REQUEST_INSTALL_PACKAGES)

    /**
     * 检查当前读写权限
     *
     * @param activity
     */
    fun checkWriteStoragePermission(activity: Activity,permissionListener: PermissionListener) {
        LogTool.d(TAG, MessageConstants.CHECK_WRITE_STORAGE_PERMISSION)
        try {
            //检测是否有写的权限
            val permission = ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(
                    activity, PERMISSIONS_STORAGE,
                    Constants.REQUEST_CODE_EXTERNAL_STORAGE
                )
            } else {
                checkInstallPermission(activity,permissionListener)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogTool.e(TAG, e.toString())
        }

    }

    /**
     * 检查当前读写权限
     *
     * @param activity
     */
    fun checkInstallPermission(activity: Activity,permissionListener: PermissionListener) {
        try {
            //1:判断是否是8.0系统,是的话需要获取此权限，判断开没开，没开的话处理未知应用来源权限问题,否则直接安装
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //2:检测是否有写的权限
                val permission = activity.packageManager.canRequestPackageInstalls()
                if (permission) {
                    permissionListener?.let { it.getPermissionSuccess() }
                } else {
                    // 3：没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(
                        activity, PERMISSIONS_INSTALL,
                        Constants.REQUEST_CODE_INSTALL
                    )
                }

            } else {
                permissionListener?.let { it.getPermissionSuccess() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogTool.e(TAG, e.toString())
        }

    }
}