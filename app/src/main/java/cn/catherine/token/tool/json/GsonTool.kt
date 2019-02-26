package cn.catherine.token.tool.json

import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.RequestJson
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.gson.gsonTypeAdapter.RequestJsonTypeAdapter
import cn.catherine.token.gson.gsonTypeAdapter.ResponseJsonTypeAdapter
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.encryption.AESTool
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import okhttp3.MediaType
import okhttp3.RequestBody
import java.lang.reflect.Type

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 14:51
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.json
+--------------+---------------------------------
+ description  +   工具類：Gson格式管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object GsonTool {
    /*将对象转换为String*/
    fun string(o: Any?): String {
        o ?: return MessageConstants.Empty
        val gson = getGson()
        return gson.toJson(o)
    }

    /*通过传入的key得到相应的数组数据*/
    fun <T> getListByKey(resource: String, key: String, type: Type): Any? {
        val gson = getGson()
        val value = JsonTool.getString(resource, key)
        return if (!StringTool.isEmpty(value) && !StringTool.equals("[]", value.replace(" ", ""))) gson.fromJson<Any>(
            value,
            type
        ) else null
    }

    /*通过传入的key得到相应的数据*/
    fun <T> getBeanByKey(resource: String, key: String, type: Type): Any? {
        val gson = getGson()
        val value = JsonTool.getString(resource, key)
        return if (StringTool.isEmpty(value)) null else gson.fromJson<Any>(value, type)
    }

    /*解析数据是object的情况*/
    @Throws(JsonSyntaxException::class)
    fun <T> convert(str: String, cls: Class<T>): T {
        val gson = getGson()
        return gson.fromJson(str, cls)
    }

    @Throws(JsonSyntaxException::class)
    fun <T> convert(str: String, type: Type): T? {
        val gson = getGson()
        return gson.fromJson<T>(str, type)
    }

    /*   encryption request bean*/
    fun <T> AESJsonBean(jsonBean: T?): String? {
        if (jsonBean == null) {
            throw NullPointerException("AESJsonBean jsonBean is null")
        }
        val json = GsonTool.string(jsonBean)
        // encryption
        var encodeJson: String? = null
        try {
            encodeJson = AESTool.encodeCBC_128(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return encodeJson
    }

    fun stringToRequestBody(str: String): RequestBody? {
        return if (StringTool.isEmpty(str)) null else RequestBody.create(MediaType.parse("application/json"), str)
    }

    fun <T> beanToRequestBody(jsonBean: T): RequestBody {
        val str = AESJsonBean(jsonBean)
        if (StringTool.isEmpty(str)) {
            throw NullPointerException("beanToRequestBody str is null")
        }
        return RequestBody.create(MediaType.parse("application/json"), str!!)
    }

    fun getGson(): Gson {
        return GsonBuilder()
            .disableHtmlEscaping()
            .create()
    }

    // 加上排序的TypeAdapter for RequestJson
    fun getGsonTypeAdapterForRequestJson(): Gson {

        return GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(RequestJson::class.java, RequestJsonTypeAdapter())
            .create()
    }

    // 加上排序的TypeAdapter for ResponseJson
    fun getGsonTypeAdapterForResponseJson(): Gson {

        return GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(ResponseJson::class.java, ResponseJsonTypeAdapter())
            .create()
    }

    fun <T> logInfo(TAG: String, flag: String, info: T) {
        LogTool.d(TAG, flag, GsonTool.string(info))
    }

    fun <T> logInfo(TAG: String, stuff: String, flag: String, info: T) {
        LogTool.d(TAG, stuff, flag, GsonTool.string(info))
    }
}