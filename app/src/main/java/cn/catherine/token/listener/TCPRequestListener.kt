package cn.catherine.token.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 11:05
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.listener
+--------------+---------------------------------
+ description  +    回調監聽：當前界面得到TCP讀取服務器的數據根據需要響應回調做出界面上的改動
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface TCPRequestListener {
    /*发送失败*/
    abstract fun sendTransactionFailure(message: String)

    /*发送成功*/
    abstract fun sendTransactionSuccess(message: String)

    /*显示当前余额*/
    abstract fun showWalletBalance(i: String)

    /*跳转修改授权代表*/
    abstract fun getPreviousModifyRepresentative(representative: String)

    /*修改授权代表结果*/
    abstract fun modifyRepresentativeResult(currentStatus: String, isSuccess: Boolean, code: Int)

    /*跳转登录*/
    abstract fun reLogin()

    /*余额不足*/
    abstract fun noEnoughBalance()

    /*金额异常*/
    abstract fun amountException()

    /*tcp返回数据异常*/
    abstract fun tcpResponseDataError(nullWallet: String)

    /*获取数据异常*/
    abstract fun getDataException(message: String)

    abstract fun refreshTransactionRecord()

    //「签章成功」显示通知提示
    abstract fun showNotification(blockService: String, amount: String)

    /*刷新TCP连接的IP信息*/
    abstract fun refreshTCPConnectIP(ip: String)

    abstract fun resetSuccess()

    abstract fun needUnbindService()

    /*余额正在同步中：BALANCE_DATA_IS_SYNCHRONIZING*/
    abstract fun balanceIsSynchronizing()
}