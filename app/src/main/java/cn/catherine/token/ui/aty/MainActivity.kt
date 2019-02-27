package cn.catherine.token.ui.aty

import android.os.Bundle
import android.view.ViewGroup
import cn.catherine.token.R
import cn.catherine.token.base.BaseActivity
import cn.catherine.token.base.BaseApplication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun getArgs(bundle: Bundle) {
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun initViews() {
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

    override fun initListener() {
    }

}
