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
+ description  +   回調監聽：Http 請求關於「交易」接口的監聽
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface HttpTransactionListener {
    //交易记录已经存在
    abstract fun transactionAlreadyExists()

    //Http请求 「签章区块」成功
    abstract fun receiveBlockHttpSuccess()

    //Http请求 「签章区块」失败
    abstract fun receiveBlockHttpFailure()
}