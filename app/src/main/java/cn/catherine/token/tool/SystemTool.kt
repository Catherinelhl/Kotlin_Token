package cn.catherine.token.tool

import java.io.DataOutputStream
import java.io.IOException

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 16:09
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool
+--------------+---------------------------------
+ description  +   工具類：系統權限
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object SystemTool {
    private val TAG = SystemTool::class.java.simpleName

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     *
     * @param command 命令：String apkRoot="chmod 777 "+getPackageCodePath();
     * @return 0 命令执行成功
     */
    fun RootCommand(command: String): Int {
        var process: Process? = null
        var os: DataOutputStream? = null
        try {
            process = Runtime.getRuntime().exec("su")
            os = DataOutputStream(process!!.outputStream)
            os.writeBytes(command + "\n")
            os.writeBytes("exit\n")
            os.flush()
            val i = process.waitFor()

            LogTool.d(TAG, "i:$i")
            return i
        } catch (e: Exception) {
            LogTool.d(TAG, e.message)
            return -1
        } finally {
            try {
                os?.close()
                process!!.destroy()
            } catch (e: Exception) {
                LogTool.e(TAG, e.toString())
            }

        }
    }

    /**
     * 提升读写权限
     *
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    fun setPermission(filePath: String) {
        val command = "chmod 777 $filePath"
        val runtime = Runtime.getRuntime()
        try {
            runtime.exec(command)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}