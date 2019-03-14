package cn.catherine.token.ui.constract

import java.io.File

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午4:48
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.constract
+--------------+---------------------------------
+ description  +  連接界面和數據操作互動：「检查钱包信息」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface CheckWalletInfoContract {
    interface View {
        fun getWalletFileSuccess()

        fun getWalletFileFailed()

    }

    interface Presenter {
        fun getWalletFileFromDB(file: File)
    }
}