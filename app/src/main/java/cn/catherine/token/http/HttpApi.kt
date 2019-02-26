package cn.catherine.token.http

import cn.catherine.token.constant.APIURLConstants
import cn.catherine.token.constant.APIURLConstants.API_SFN_WALLET_LOGIN
import cn.catherine.token.gson.ResponseJson
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 14:20
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.http
+--------------+---------------------------------
+ description  +   Http请求网络的所有接口組裝
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface HttpApi {
    /************** SFN  */
    // 登入  @FormUrlEncoded
    @POST(API_SFN_WALLET_LOGIN)
    abstract fun login(@Body requestBody: RequestBody): Call<ResponseJson>

    /*SFN：登出*/
    @POST(APIURLConstants.API_SFN_WALLET_LOGOUT)
    abstract fun logout(@Body requestBody: RequestBody): Call<ResponseJson>

    /*SFN：验证当前token是否过期*/
    @POST(APIURLConstants.API_SFN_WALLET_VERIFY)
    abstract fun verify(@Body requestBody: RequestBody): Observable<ResponseJson>

    /*SFN：當錢包與AuthNode無法通信時調用,取得新的AuthNode IP資訊*/
    @POST(APIURLConstants.API_SFN_WALLET_RESET_AUTH_NODE_INFO)
    abstract fun resetAuthNodeInfo(@Body requestBody: RequestBody): Observable<ResponseJson>


    /************** SAN  */
    /*AN："取最新的區塊 & wallet餘額"*/
    /* 每次发送之前需要请求*/
    @POST(APIURLConstants.API_SAN_WALLET_GET_LATEST_BLOCK_AND_BALANCE)
    abstract fun getLastedBlockAndBalance(@Body requestBody: RequestBody): Call<ResponseJson>

    /*AN："取得未簽章R區塊的Send區塊 & 取最新的R區塊 & wallet餘額"*/
    /*由TCP和服务器建立长连接，进行定时的拉取数据*/
    @POST(APIURLConstants.API_SAN_WALLET_GET_WALLET_WAITING_TO_RECEIVE_BLOCK)
    abstract fun getWalletWaitingToReceiveBlock(@Body requestBody: RequestBody): Observable<ResponseJson>

    /*单独获取余额*/
    @POST(APIURLConstants.API_SAN_WALLET_GET_BALANCE)
    abstract fun getBalance(@Body requestBody: RequestBody): Observable<ResponseJson>

    /*AN：TC Send*/
    @POST(APIURLConstants.API_SAN_WALLET_TRANSACTION_CHAIN_SEND)
    abstract fun send(@Body requestBody: RequestBody): Call<ResponseJson>

    /*AN：TC receiver*/
    @POST(APIURLConstants.API_SAN_WALLET_TRANSACTION_CHAIN_RECEIVE)
    abstract fun receive(@Body requestBody: RequestBody): Call<ResponseJson>

    /*AN：獲取最新的更換委託人區塊*/
    @POST(APIURLConstants.API_SAN_WALLET_GET_LATEST_CHANGE_BLOCK)
    abstract fun getLatestChangeBlock(@Body requestBody: RequestBody): Call<ResponseJson>

    /*AN：TC change*/
    @POST(APIURLConstants.API_SAN_WALLET_CHANGE)
    abstract fun change(@Body requestBody: RequestBody): Call<ResponseJson>


    /************** API  */

    /*獲取幣種清單 API*/
    @POST(APIURLConstants.API_GET_BLOCK_SERVICE_LIST)
    abstract fun getBlockServiceList(@Body requestBody: RequestBody): Call<ResponseJson>

    /*獲取已完成交易 API*/
    @POST(APIURLConstants.API_ACCOUNT_DONE_TC)
    abstract fun getAccountDoneTC(@Body requestBody: RequestBody): Call<ResponseJson>


    /*获取当前Wallet的外网IP*/
    @POST(APIURLConstants.API_GET_MY_IP_INFO)
    abstract fun getMyIpInfo(@Body requestBody: RequestBody): Observable<ResponseJson>

    /************** UPDATE  */
    /*检查更新Android版本信息*/
    @POST(APIURLConstants.API_GET_ANDROID_VERSION_INFO)
    abstract fun getAndroidVersionInfo(@Body requestBody: RequestBody): Call<ResponseJson>
}