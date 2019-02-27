package cn.catherine.token.manager

import android.content.Context
import android.graphics.drawable.Drawable
import cn.catherine.token.R
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.constant.MessageConstants
import java.util.ArrayList

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/27 14:45
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.manager
+--------------+---------------------------------
+ description  +   数据生成寄存器
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class DataGenerationManager {
    //底部tab的标题集合
    private val tabBottomTitles = ArrayList<String>()
    //底部tab的图标集合
    private val tabBottomDrawables = ArrayList<Int>()
    //底部tab的选中图标集合
    private val tabBottomFocusDrawables = ArrayList<Int>()
    //底部tab的数量
    private var tabBottomTitleCount: Int = 0

    init {
        //初始化底部栏文本数据
        tabBottomTitles.add(BaseApplication.context.resources.getString(R.string.home))
        tabBottomTitles.add(BaseApplication.context.resources.getString(R.string.receive))
        tabBottomTitles.add(BaseApplication.context.resources.getString(R.string.send))
        tabBottomTitles.add(BaseApplication.context.resources.getString(R.string.settings))
        tabBottomTitleCount = tabBottomTitles.size

        //初始化底部栏图标数据
        tabBottomDrawables.add(R.mipmap.icon_home)
        tabBottomDrawables.add(R.mipmap.icon_receive)
        tabBottomDrawables.add(R.mipmap.icon_send)
        tabBottomDrawables.add(R.mipmap.icon_setting)
        tabBottomFocusDrawables.add(R.mipmap.icon_home_f)
        tabBottomFocusDrawables.add(R.mipmap.icon_receive_f)
        tabBottomFocusDrawables.add(R.mipmap.icon_send_f)
        tabBottomFocusDrawables.add(R.mipmap.icon_setting_f)
    }

    /**
     * 根据底部栏当前的位置返回当前位置信息
     *
     * @param position
     * @return
     */
    fun getTabBottomTitle(position: Int): String {
        return if (position >= tabBottomTitleCount) {
            MessageConstants.Empty
        } else tabBottomTitles[position]
    }

    /**
     * 根据当前底部栏的位置返回当前位置上图标信息
     *
     * @param position
     * @return
     */
    private fun getTabBottomDrawable(position: Int, isSelect: Boolean): Int {
        if (position >= tabBottomTitleCount) {
            return 0
        }
        return if (isSelect) tabBottomFocusDrawables[position] else tabBottomDrawables[position]
    }

    /**
     * 根据位置下标，是否是选中的状态
     *
     * @param i
     * @param isSelect
     * @return
     */
    fun getDrawableTop(context: Context, i: Int, isSelect: Boolean): Drawable {
        return context.resources.getDrawable(getTabBottomDrawable(i, isSelect))

    }

}