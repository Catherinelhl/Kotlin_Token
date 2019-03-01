package cn.catherine.token.manager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.catherine.token.R
import cn.catherine.token.tool.LogTool
import cn.catherine.token.ui.aty.LoginActivity

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/1 10:43
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.manager
+--------------+---------------------------------
+ description  +   APP管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AppManager {

    private var activity: Activity? = null

    constructor()
    constructor(activity: Activity) {
        this.activity = activity
    }

    fun cleanAccountData() {}

    fun cleanQueueTask() {}
    /**
     * 跳转登录页面
     */
    fun intentToLogin() {
        //如果當前是phone，那麼就跳轉到手機的登錄頁面，否則跳轉到TV的登錄頁面
        intentToActivity(LoginActivity::class.java, true)
    }

    /**
     * 从当前页面跳转到另一个页面
     *
     * @param classTo
     */
    fun intentToActivity(classTo: Class<*>) {
        intentToActivity(null, classTo)
    }

    /**
     * @param finishFrom 是否关闭上一个activity，默认是不关闭 false
     */
    fun intentToActivity(classTo: Class<*>, finishFrom: Boolean) {
        intentToActivity(null, classTo, finishFrom)
    }

    /**
     * @param bundle 存储当前页面需要传递的数据
     */
    fun intentToActivity(bundle: Bundle?, classTo: Class<*>) {
        intentToActivity(bundle, classTo, false)
    }

    /**
     * 頁面跳轉
     *
     * @param bundle
     * @param classTo
     * @param finishFrom 是否
     */
    fun intentToActivity(bundle: Bundle?, classTo: Class<*>, finishFrom: Boolean) {
        this.intentToActivity(bundle, classTo, finishFrom, false)
    }

    /**
     * 頁面跳轉
     *
     * @param bundle
     * @param classTo
     * @param finishFrom
     * @param isClearTask 是否清空任務
     */
    fun intentToActivity(bundle: Bundle?, classTo: Class<*>, finishFrom: Boolean, isClearTask: Boolean) {
        activity?.let {
            val intent = Intent()
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            if (isClearTask) {
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.setClass(it, classTo)
            it.startActivity(intent)
            if (finishFrom) {
                it.finish()
            }
            it.overridePendingTransition(R.anim.slide_in_alpha, R.anim.slide_exit_alpha)
        }

    }
}