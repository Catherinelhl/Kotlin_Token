package cn.catherine.token.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.event.NetStateChangeEvent
import cn.catherine.token.tool.OttoTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 18:00
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.receiver
+--------------+---------------------------------
+ description  +   廣播：監聽系統網絡變化然後進行廣播
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class NetStateReceiver : BroadcastReceiver() {
    private var connectivityManager: ConnectivityManager? = null
    private var networkInfo: NetworkInfo? = null

    init {
        OttoTool.register(BaseApplication.context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == ConnectivityManager.CONNECTIVITY_ACTION) {
            connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            networkInfo = connectivityManager!!.activeNetworkInfo
            if (networkInfo == null || !networkInfo!!.isConnectedOrConnecting) {
                OttoTool.post(NetStateChangeEvent(false))
            } else {
                OttoTool.post(NetStateChangeEvent(true))
            }
        }
    }
}