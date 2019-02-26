package cn.catherine.token.tool

import android.os.Handler
import android.os.Looper
import com.squareup.otto.Bus

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 17:59
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool
+--------------+---------------------------------
+ description  +   工具類：事件監聽訂閱
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object OttoTool : Bus() {
    /**
     * 通过单例模式返回唯一的bus对象,而且重写父类的post方法,通过handler实现任意线程可以调用
     */
    private val mHandler = Handler(Looper.getMainLooper())


    override fun post(event: Any) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event)
        } else {
            mHandler.post { super@OttoTool.post(event) }
        }
    }
}