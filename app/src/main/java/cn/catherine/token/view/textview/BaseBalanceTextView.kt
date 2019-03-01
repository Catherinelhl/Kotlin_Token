package cn.catherine.token.view.textview

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.catherine.token.base.BaseApplication.Companion.context
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.decimal.DecimalTool
import cn.catherine.token.tool.pop.ShowDetailPopWindow

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 16:25
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.view
+--------------+---------------------------------
+ description  +   自定義TextView：用于顯示界面顯示余额
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseBalanceTextView(context: Context, private val attrs: AttributeSet?) : TextView(context, attrs) {
    constructor(context: Context) : this(context, null)

    //是否显示pop
    private var showPop: Boolean = false

    init {
        this.setOnClickListener {
            if (isShowPop()) {
                showDetailPop(this@BaseBalanceTextView, text.toString())
            }

        }

    }

    fun isShowPop(): Boolean {
        return showPop
    }

    fun setShowPop(showPop: Boolean) {
        this.showPop = showPop
    }

    //对当前的余额进行赋值
    fun setBalance(balance: String) {
        var balance: String? = balance
        if (StringTool.isEmpty(balance)) {
            balance = GlobalVariableManager.walletBalance
            if (StringTool.isEmpty(balance)) {
                balance = "0"
            }
        }
        text = DecimalTool().transferDisplay(balance)
        this.ellipsize = TextUtils.TruncateAt.END
    }

    /**
     * 顯示完整的信息：金额/地址/私钥
     *
     * @param view 需要依賴的視圖
     */
    fun showDetailPop(view: View, content: String) {
        val window = ShowDetailPopWindow(context, content)
        val contentView = window.contentView
        //需要先测量，PopupWindow还未弹出时，宽高为0
        contentView.measure(
            makeDropDownMeasureSpec(window.width),
            makeDropDownMeasureSpec(window.height)
        )
        val offsetX = Math.abs(window.contentView.measuredWidth - view.width) / 2
        val offsetY = -(window.contentView.measuredHeight + view.height)
        window.showAsDropDown(view, offsetX, offsetY, Gravity.START)

    }


    private fun makeDropDownMeasureSpec(measureSpec: Int): Int {
        val mode: Int
        if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mode = View.MeasureSpec.UNSPECIFIED
        } else {
            mode = View.MeasureSpec.EXACTLY
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode)
    }
}