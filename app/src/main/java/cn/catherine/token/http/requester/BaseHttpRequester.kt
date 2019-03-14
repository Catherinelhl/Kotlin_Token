package cn.catherine.token.http.requester

import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.http.HttpApi
import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.StringTool
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Callback

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午5:36
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.http.requester
+--------------+---------------------------------
+ description  +   Requester:所有网络请求前數據組裝以及響應結果的交互器
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseHttpRequester {

    //获取钱包余额以及R区块，长连接
    fun getWalletWaitingToReceiveBlock(body: RequestBody): Observable<ResponseJson> {
        val baseUrl = GlobalVariableManager.getANHttpAddress()
        if (StringTool.isEmpty(baseUrl)) {
            return Observable.empty<ResponseJson>()
        }
        return RetrofitFactory.getAnInstance(baseUrl!!)
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.getWalletWaitingToReceiveBlock(body) }!!

    }


    //单独获取钱包
    fun getBalance(body: RequestBody): Observable<ResponseJson> {
        val baseUrl = GlobalVariableManager.getANHttpAddress()
        if (StringTool.isEmpty(baseUrl)) {
            return Observable.empty<ResponseJson>()
        }
        return RetrofitFactory.getAnInstance(baseUrl!!)
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.getBalance(body) }!!
    }

    //获取最新余额
    fun getLastBlockAndBalance(body: RequestBody, callBackListener: Callback<ResponseJson>) {
        val baseUrl = GlobalVariableManager.getANHttpAddress()
        if (StringTool.isEmpty(baseUrl)) {
            return
        }
        RetrofitFactory.getAnInstance(baseUrl!!)
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.getLastedBlockAndBalance(body) }
            ?.let { it.enqueue(callBackListener) }

    }


    //重新拿去AN的信息
    fun resetAuthNode(body: RequestBody): Observable<ResponseJson> {
        return RetrofitFactory.getInstance()
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.resetAuthNodeInfo(body) }!!
    }

    //拿去幣種清單的信息
    fun getBlockServiceList(body: RequestBody, callBackListener: Callback<ResponseJson>) {
        RetrofitFactory.getAPIInstance()
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.getBlockServiceList(body) }
            ?.let { it.enqueue(callBackListener) }
    }

    //receive
    fun receive(body: RequestBody, callBackListener: Callback<ResponseJson>) {
        val baseUrl = GlobalVariableManager.getANHttpAddress()
        if (StringTool.isEmpty(baseUrl)) {
            return
        }
        RetrofitFactory.getAnInstance(baseUrl!!)
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.receive(body) }
            ?.let { it.enqueue(callBackListener) }
    }

    //send
    fun send(body: RequestBody, callBackListener: Callback<ResponseJson>) {
        val baseUrl = GlobalVariableManager.getANHttpAddress()
        if (StringTool.isEmpty(baseUrl)) {
            return
        }
        RetrofitFactory.getAnInstance(baseUrl!!)
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.send(body) }
            ?.let { it.enqueue(callBackListener) }
    }

    fun getAndroidVersionInfo(requestBody: RequestBody, callBackListener: Callback<ResponseJson>) {
        RetrofitFactory.getUpdateInstance()
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.getAndroidVersionInfo(requestBody) }
            ?.let { it.enqueue(callBackListener) }
    }

    //获取已完成交易 API
    fun getAccountDoneTC(requestBody: RequestBody, callBackListener: Callback<ResponseJson>) {
        RetrofitFactory.getAPIInstance()
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.getAccountDoneTC(requestBody) }
            ?.let { it.enqueue(callBackListener) }
    }


    /* 取最新的更換委託人區塊*/
    fun getLastChangeBlock(body: RequestBody, callBackListener: Callback<ResponseJson>) {
        val baseUrl = GlobalVariableManager.getANHttpAddress()
        if (StringTool.isEmpty(baseUrl)) {
            return
        }
        RetrofitFactory.getAnInstance(baseUrl!!)
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.getLatestChangeBlock(body) }
            ?.let { it.enqueue(callBackListener) }
    }

    /* TC change*/
    fun change(body: RequestBody, callBackListener: Callback<ResponseJson>) {
        val baseUrl = GlobalVariableManager.getANHttpAddress()
        if (StringTool.isEmpty(baseUrl)) {
            return
        }
        RetrofitFactory.getAnInstance(baseUrl!!)
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.change(body) }
            ?.let { it.enqueue(callBackListener) }
    }

    /*「登出」當前帳戶*/
    fun logout(body: RequestBody, callBackListener: Callback<ResponseJson>) {
        RetrofitFactory.getInstance()
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.logout(body) }
            ?.let { it.enqueue(callBackListener) }
    }

    fun login(body: RequestBody, callBackListener: Callback<ResponseJson>) {
        RetrofitFactory.getInstance()
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.login(body) }
            ?.let { it.enqueue(callBackListener) }
    }

    fun verify(body: RequestBody): Observable<ResponseJson> {
        return RetrofitFactory.getInstance()
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.verify(body) }!!
    }

    fun getMyIpInfo(body: RequestBody): Observable<ResponseJson> {
        return RetrofitFactory.getAPIInstance()
            ?.let { it.create(HttpApi::class.java) }
            ?.let { it.getMyIpInfo(body) }!!
    }
}