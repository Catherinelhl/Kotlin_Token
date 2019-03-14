package cn.catherine.token.ui.presenter

import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.ui.constract.CheckWalletInfoContract
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午5:02
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.presenter
+--------------+---------------------------------
+ description  +  Presenter：「錢包信息」界面相關的數據獲取&處理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CheckWalletInfoPresenterImp(private val view: CheckWalletInfoContract.View) : CheckWalletInfoContract.Presenter {

    private val TAG = CheckWalletInfoPresenterImp::class.java.simpleName
    override fun getWalletFileFromDB(file: File) {
        //1:取出当前数据
        val walletBean = GlobalVariableManager.walletBean
        if (walletBean == null) {
            view.getWalletFileFailed()
        } else {
            val keyStore = GsonTool.getGson().toJson(walletBean)
            //2：将数据写入本地文件
            if (writeKeyStoreToFile(keyStore, file)) {
                view.getWalletFileSuccess()
            } else {
                view.getWalletFileFailed()

            }
        }
    }


    /*存储钱包信息*/
    private fun writeKeyStoreToFile(keystore: String, file: File): Boolean {
        var status = false
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            fos.write(keystore.toByteArray())
            status = true
        } catch (e: IOException) {
            LogTool.d(TAG, e.message)
            e.printStackTrace()
            status = false
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    LogTool.d(TAG, e.message)
                    e.printStackTrace()
                }

            }
            return status
        }
    }
}