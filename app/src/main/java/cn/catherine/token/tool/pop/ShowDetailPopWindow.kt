package cn.catherine.token.tool.pop

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import cn.catherine.token.R
import cn.catherine.token.event.RefreshWalletBalanceEvent
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.OttoTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.decimal.DecimalTool
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.popwindow_show_amount.view.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 16:30
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.pop
+--------------+---------------------------------
+ description  +    自定義PopWindow：用于金额显示不完全，点击显示完全的金额
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class ShowDetailPopWindow(context: Context, content: String) : PopupWindow(context) {
    private var textView: TextView? = null

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView = LayoutInflater.from(context).inflate(
            R.layout.popwindow_show_amount,
            null, false
        )
        contentView.tv_amount!!.text = if (StringTool.isEmpty(content)) "0.0000000" else content
        setContentView(contentView)
        OttoTool.register(this)
    }

    /*更新钱包余额*/
    @Subscribe
    fun refreshWalletBalance(refreshWalletBalanceEvent: RefreshWalletBalanceEvent?) {
        if (refreshWalletBalanceEvent == null) {
            return
        }
        if (textView != null) {
            textView!!.text = DecimalTool().transferDisplay(GlobalVariableManager.walletBalance)
        }
    }
}