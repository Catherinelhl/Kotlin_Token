package cn.catherine.token.ui.constract

import cn.catherine.token.base.BaseContract

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午4:37
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.constract
+--------------+---------------------------------
+ description  +    連接界面和數據操作互動：「登錄」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface LoginContracts {

    interface View : BaseContract.View {
        abstract fun noWalletInfo() //当前没有钱包，需要用户创建或者导入

        abstract fun loginFailure() //登录失败

        abstract fun loginSuccess()

        abstract fun passwordError()

        /*更新版本，是否强制更新*/
        abstract fun updateVersion(forceUpgrade: Boolean, appStoreUrl: String, updateUrl: String)

        abstract fun getAndroidVersionInfoFailure() //檢查更新失敗
    }

    interface Presenter {
        abstract fun queryWalletFromDB(password: String)

        abstract fun getRealIpForLoginRequest()

        //获取BCAASC Android版本信息，查看是否需要更新
        abstract fun getAndroidVersionInfo()
    }
}