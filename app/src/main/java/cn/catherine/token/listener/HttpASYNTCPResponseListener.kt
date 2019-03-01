package cn.catherine.token.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 11:04
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.listener
+--------------+---------------------------------
+ description  +   回調監聽：Http 异步响应给TCP数据
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface HttpASYNTCPResponseListener {
    //获取最后change区块成功
    abstract fun getLatestChangeBlockSuccess()

    //获取最后Change区块失败
    abstract fun getLatestChangeBlockFailure(failure: String)

    abstract fun resetSuccess()

    abstract fun resetFailure()

    abstract fun logout()

    //发送交易失败
    abstract fun sendFailure()

    //可以reset SAN
    abstract fun canReset()

    abstract fun verifySuccess(from: String)

    abstract fun verifyFailure(from: String)
}