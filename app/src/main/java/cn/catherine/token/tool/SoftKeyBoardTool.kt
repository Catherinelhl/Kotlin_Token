package cn.catherine.token.tool

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/1 10:08
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool
+--------------+---------------------------------
+ description  +   软键盘管理工具
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SoftKeyBoardTool(private val activity: Activity?) {
    /*键盘输入管理者*/
    private var inputMethodManager: InputMethodManager? = null

    init {
        activity?.let {
            inputMethodManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        }
    }

    /*隱藏當前軟鍵盤*/
    fun hideSoftKeyboard() {
        activity ?: return
        inputMethodManager ?: return
        if (activity.currentFocus != null) {
            inputMethodManager!!.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}