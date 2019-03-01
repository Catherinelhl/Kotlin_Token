package cn.catherine.token.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 11:03
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.listener
+--------------+---------------------------------
+ description  +   回調監聽：Http 請求但錢wallet 外网Ip地址結果回调
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface GetMyIpInfoListener {
    abstract fun responseGetMyIpInfo(isSuccess: Boolean)

}