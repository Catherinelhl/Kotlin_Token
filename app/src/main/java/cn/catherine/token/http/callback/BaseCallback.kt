package cn.catherine.token.http.callback

import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.LogTool
import retrofit2.Call
import retrofit2.Response

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 15:51
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.http.callback
+--------------+---------------------------------
+ description  +   自定義Http請求結果回應過濾
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

abstract class BaseCallback<T : Any> : retrofit2.Callback<T> {
    private val tag = BaseCallback::class.java.simpleName

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val code = response.raw().code()
        if (code == MessageConstants.CODE_404 || code == MessageConstants.CODE_400) {
            LogTool.d<String>(tag, "internet response: " + MessageConstants.CODE_404)
            onNotFound()
        } else {
            if (response.raw().isSuccessful) {
                onSuccess(response)
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {

    }


    abstract fun onSuccess(response: Response<T>)

    open fun onNotFound() {
        return
    }
}