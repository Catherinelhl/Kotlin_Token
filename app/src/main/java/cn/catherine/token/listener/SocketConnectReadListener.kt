package cn.catherine.token.listener

import cn.catherine.token.gson.ResponseJson

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/19 16:50
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.listener
+--------------+---------------------------------
+ description  +   socket连接读取数据回调
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface SocketConnectReadListener {
    fun getReadData(responseJson: ResponseJson?)
    fun closeConnectRead()
}