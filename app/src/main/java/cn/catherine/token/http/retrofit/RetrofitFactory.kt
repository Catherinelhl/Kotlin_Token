package cn.catherine.token.http.retrofit

import cn.catherine.token.constant.Constants
import cn.catherine.token.tool.ServerTool
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 16:42
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.http.retrofit
+--------------+---------------------------------
+ description  +   Http：Retrofit封裝网络请求
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object RetrofitFactory {

    private var SFNInstance: Retrofit? = null
    private var ANInstance: Retrofit? = null//访问AN的网络
    private var APIInstance: Retrofit? = null//访问正常訪問的网络
    private var UpdateInstance: Retrofit? = null//检查更新
    private var client: OkHttpClient? = null

    private fun initClient() {
        if (client == null) {
            client = OkHttpClient.Builder()
                .connectTimeout(Constants.Time.LONG_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.Time.LONG_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.Time.LONG_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(OkHttpInterceptor())
                .build()
        }
    }

    fun getInstance(): Retrofit? {
        var serverBean = ServerTool.getDefaultServerBean()
        if (serverBean == null) {
            serverBean = ServerTool.getDefaultServerBean()
            if (serverBean == null) {
                return null
            }
        }
        return getSFNInstance(serverBean.sfnServer)
    }

    /**
     * SFN api
     *
     * @param baseUrl
     * @return
     */
    fun getSFNInstance(baseUrl: String): Retrofit? {
        initClient()
        if (SFNInstance == null) {
            SFNInstance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client!!)
                .addConverterFactory(StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Observable，暂时没用
                .build()
        }
        return SFNInstance
    }

    /**
     * AN api
     *
     * @param baseUrl
     * @return
     */
    fun getAnInstance(baseUrl: String): Retrofit? {
        initClient()
        ANInstance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client!!)
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Observable，暂时没用
            .build()
        return ANInstance
    }

    /**
     * Application api
     *
     * @return
     */
    fun getAPIInstance(): Retrofit? {
        initClient()
        val serverBean = ServerTool.getDefaultServerBean()
        var apiServer: String? = null
        if (serverBean != null) {
            apiServer = serverBean!!.getApiServer()
        }
        APIInstance = Retrofit.Builder()
            .baseUrl(apiServer!!)
            .client(client!!)
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Observable，暂时没用
            .build()
        return APIInstance
    }

    /**
     * update server api
     *
     * @return
     */
    fun getUpdateInstance(): Retrofit? {
        initClient()
        val serverBean = ServerTool.getDefaultServerBean()
        var updateServer: String? = null
        if (serverBean != null) {
            updateServer = serverBean!!.getUpdateServer()
        }
        UpdateInstance = Retrofit.Builder()
            .baseUrl(updateServer!!)
            .client(client!!)
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Observable，暂时没用
            .build()
        return UpdateInstance
    }

    //清空当前所有请求的缓存数据信息
    fun clean() {
        cleanSFN()
        cleanAN()
        cleanAPI()
        UpdateInstance = null
    }

    //清空当前的SFN请求
    fun cleanSFN() {
        SFNInstance = null
    }

    //清空AN请求
    fun cleanAN() {
        ANInstance = null

    }

    //清空API请求
    fun cleanAPI() {
        APIInstance = null
    }


}