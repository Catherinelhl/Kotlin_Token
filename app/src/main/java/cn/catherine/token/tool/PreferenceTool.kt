package cn.catherine.token.tool

import android.content.SharedPreferences
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.constant.Constants

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 15:28
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool
+--------------+---------------------------------
+ description  +   工具類：用于存储当前APP需要用的值於SharedPreferences
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object PreferenceTool {
    private val TAG = PreferenceTool::class.java.simpleName
    private val sp: SharedPreferences by lazy {
        BaseApplication.context().getSharedPreferences(Constants.Preference.SP_BCAAS_TUTORIAL_PAGE, 0)
    }
    private val editor: SharedPreferences.Editor by lazy { sp.edit() }

    fun getString(key: String): String? {
        return this.getString(key, "")
    }

    fun getString(key: String, defValue: String): String? {
        return sp.getString(key, defValue)
    }

    fun getInt(key: String): Int {
        return this.getInt(key, 0)
    }

    fun getInt(key: String, defValue: Int): Int {
        return sp.getInt(key, defValue)
    }

    fun getBoolean(key: String): Boolean {
        return this.getBoolean(key, false)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sp.getBoolean(key, defValue)
    }

    fun getLong(key: String): Long? {
        return this.getLong(key, 0L)
    }

    fun getLong(key: String, defValue: Long?): Long? {
        return sp.getLong(key, defValue!!)
    }

    fun getFloat(key: String): Float? {
        return this.getFloat(key, 0.0f)
    }

    fun getFloat(key: String, defValue: Float?): Float? {
        return sp.getFloat(key, defValue!!)
    }

    fun saveString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun saveInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.commit()
    }

    fun saveLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }

    fun clear(key: String) {
        editor.remove(key)
        editor.commit()
    }
}