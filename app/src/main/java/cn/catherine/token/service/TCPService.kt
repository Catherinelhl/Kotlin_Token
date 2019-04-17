package cn.catherine.token.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/19 09:54
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.service
+--------------+---------------------------------
+ description  +   服務：開啟一個存創建Socket連接，保持TCP數據連線的服務
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class TCPService : Service() {

    private val TAG = TCPService::class.java.simpleName
    private val tcpIBinder: TCPBinder = TCPBinder()

    /**
     * 新建立一个Binder
     */
    inner class TCPBinder : Binder() {
        val service: TCPService
            get() = this@TCPService
    }

    override fun onBind(intent: Intent?): IBinder? =tcpIBinder

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    fun startTCP(){

    }
}