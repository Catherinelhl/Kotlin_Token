package cn.catherine.token.ui.constract

import cn.catherine.token.base.BaseContract

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午4:49
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.constract
+--------------+---------------------------------
+ description  +  連接界面和數據操作互動：「設置」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface SettingContract {
    interface View : BaseContract.View {
        fun logoutSuccess()
        fun logoutFailure(message: String)
        fun logoutFailure()
        // 账户异常
        fun accountError()
    }

    interface Presenter {
        fun logout()

    }
}