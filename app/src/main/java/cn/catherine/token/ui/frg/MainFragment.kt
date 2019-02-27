package cn.catherine.token.ui.frg

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import cn.catherine.token.R
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.base.BaseFragment
import kotlinx.android.synthetic.main.frg_main.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/27 11:19
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.frg
+--------------+---------------------------------
+ description  +   Fragment:「首页」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class MainFragment : BaseFragment() {
    override fun getLayoutRes(): Int = R.layout.frg_main

    override fun initViews(view: View) {
        //1:need to judge the screen's direction
        val layoutParam: ViewGroup.LayoutParams = top_left.layoutParams
        val width =
            (if (getScreenDirectionIsPortrait()) (if (BaseApplication.screenWidth < BaseApplication.screenHeight) BaseApplication.screenWidth else BaseApplication.screenHeight)
            else (if (BaseApplication.screenWidth < BaseApplication.screenHeight) BaseApplication.screenHeight else BaseApplication.screenWidth))
                .let { it * 47 / 50 }
        val height = width * 204 / 355
        layoutParam.width = width
        layoutParam.height = height
        top_left.layoutParams = layoutParam
    }

    override fun getArgs(bundle: Bundle) {
    }

    override fun initListener() {
    }

    fun refreshView() {

    }
}