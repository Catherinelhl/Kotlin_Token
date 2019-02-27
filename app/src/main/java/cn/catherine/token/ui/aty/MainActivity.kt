package cn.catherine.token.ui.aty

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.widget.TextView
import cn.catherine.token.R
import cn.catherine.token.base.BaseActivity
import cn.catherine.token.base.BaseFragment
import cn.catherine.token.tool.ListTool
import cn.catherine.token.tool.LogTool
import cn.catherine.token.ui.frg.MainFragment
import cn.catherine.token.ui.frg.ReceiveFragment
import cn.catherine.token.ui.frg.SendFragment
import cn.catherine.token.ui.frg.SettingFragment
import kotlinx.android.synthetic.main.aty_main.*
import kotlinx.android.synthetic.main.include_header.*
import kotlinx.android.synthetic.main.item_bottom_tab.*

class MainActivity : BaseActivity() {

    //存储Fragment的数组
    private var fragments: MutableList<BaseFragment> = arrayListOf()
    //得到当前显示的Fragment
    private var currentFragment: BaseFragment? = null

    override fun getArgs(bundle: Bundle) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_main

    override fun initViews() {
        //初始化「Main」页面
        val mainFragment: MainFragment = MainFragment()
        fragments.add(mainFragment)
        //初始化「Receive」页面
        val receiveFragment: ReceiveFragment = ReceiveFragment()
        fragments.add(receiveFragment)
        //初始化「Send」页面
        val sendFragment: SendFragment = SendFragment()
        fragments.add(sendFragment)
        //初始化「Setting」页面
        val settingFragment: SettingFragment = SettingFragment()
        fragments.add(settingFragment)

    }

    override fun initData() {
        for (i in fragments.indices) {
            LogTool.d(tag, "initData:$i")
            val tab = bottom_tab_layout.newTab()
            // method 自定义布局-----
            tab.setCustomView(R.layout.item_bottom_tab)
            val textView = tab.customView!!.findViewById<TextView>(R.id.tv_tab_title)
            textView.setTextColor(context.resources.getColor(R.color.black30_1d2124))
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null,
                dataGenerationManager.getDrawableTop(this, i, false),
                null,
                null
            )
            textView.text = dataGenerationManager.getTabBottomTitle(i)
            //自定义布局-----
            bottom_tab_layout.addTab(tab)
            if (i == 0) {
                //默认选中第一个
                onTabItemSelected(i)
                ll_tab_item.isSelected = true
                textView.setTextColor(context.resources.getColor(R.color.button_right_color))
                //method 2
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    dataGenerationManager.getDrawableTop(this, 0, true),
                    null,
                    null
                )
            }
        }

    }

    override fun initListener() {
        bottom_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

                tab?.let {
                    val textView = it.customView!!.findViewById<TextView>(R.id.tv_tab_title)
                    //自定义
                    textView.setTextColor(context.resources.getColor(R.color.button_right_color))
                    //method 2
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        dataGenerationManager.getDrawableTop(this@MainActivity, it.position, true),
                        null,
                        null
                    )
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //自定义
                ll_tab_item.isSelected = false

                tab?.let {
                    val textView = it.customView!!.findViewById<TextView>(R.id.tv_tab_title)

                    textView.setTextColor(context.resources.getColor(R.color.black30_1d2124))
                    //method 2
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        dataGenerationManager.getDrawableTop(this@MainActivity, it.position, false),
                        null,
                        null
                    )
                }


            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                //改变当前中间content信息；Fragment变换
                tab?.let {
                    onTabItemSelected(it.position)
                    //自定义:如果是自定义的tabItem，那么就需要调用这句来设置选中状态，虽然没有界面上的变化
                    ll_tab_item.isSelected = true
                    val textView = it.customView!!.findViewById<TextView>(R.id.tv_tab_title)
                    textView.setTextColor(context.resources.getColor(R.color.button_right_color))
                    //method 2：如果是直接就用一个TextView控件来表示了，那么就可以直接用下面这一句来表示
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        dataGenerationManager.getDrawableTop(this@MainActivity, it.position, true),
                        null,
                        null
                    )
                }

            }

        })

    }

    /**
     * 根据选中的position来切换选项卡
     *
     * @param position
     */
    private fun onTabItemSelected(position: Int) {
        LogTool.d(tag, "onTabItemSelected$position")
        if (ListTool.noEmpty(fragments) && position < fragments.size) {
            currentFragment = fragments[position]
            currentFragment?.let {
                LogTool.d(tag, "currentFragment$it")
                supportFragmentManager.beginTransaction().replace(R.id.home_container, it).commit()
            }
            when (position) {
                0 -> {
                    setTitle(getString(R.string.home))
                    if (currentFragment is MainFragment) {
                        (currentFragment as MainFragment).refreshView()
                    }
                }
                1 -> {
                    setTitle(getString(R.string.receive))
                    if (currentFragment is ReceiveFragment) {
                        (currentFragment as ReceiveFragment).refreshView()
                    }
                }
                2 -> {
                    setTitle(getString(R.string.send))
                    /*取得財務紀錄交易資訊*/
                    if (currentFragment is SendFragment) {
                        (currentFragment as SendFragment).refreshView()
                    }
                }
                3 -> {
                    setTitle(getString(R.string.settings))
                }
            }
        }

    }

    /**
     * 设置标题
     */
    private fun setTitle(title: String) {
        if (tv_title != null) {
            tv_title.text = title
        }
    }

}
