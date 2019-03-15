package cn.catherine.token.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import cn.catherine.token.R
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.tool.StringTool
import kotlinx.android.synthetic.main.layout_bcaas_single_dialog.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 16:33
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.view.dialog
+--------------+---------------------------------
+ description  +   自定義Dialog：单个按钮提示框
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseSingleDialog : Dialog {
    private var confirmClickListener: ConfirmClickListener? = null
    // define a constructor that no params
    constructor() : this(BaseApplication.context)
    constructor(context: Context) : super(context, 0)
    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bcaas_single_dialog, null)
        setContentView(view)
        initListener()
    }

    fun setLeftText(left: String): BaseSingleDialog {
        if (StringTool.isEmpty(left)) return this
        btn_sure.text = left
        return this

    }

    fun setContent(content: String): BaseSingleDialog {
        if (StringTool.isEmpty(content)) return this
        tv_content.text = content
        return this

    }

    fun setTitle(title: String): BaseSingleDialog {
        if (StringTool.isEmpty(title)) return this
        tv_title.text = title
        return this

    }

    fun initListener() {
        btn_sure.setOnClickListener { v -> confirmClickListener!!.sure() }
    }


    fun setOnConfirmClickListener(confirmClickListener: ConfirmClickListener): BaseSingleDialog {
        this.confirmClickListener = confirmClickListener
        return this
    }

    interface ConfirmClickListener {
        fun sure()
    }

    var basSingleDialog: BaseSingleDialog? = null


    fun showDialog(activity: Activity,message: String, listener: BaseSingleDialog.ConfirmClickListener) {
        showDialog(activity,context.resources.getString(R.string.warning), message, listener)
    }

    /**
     * 显示单个 按钮对话框
     *
     * @param title
     * @param message
     * @param listener
     */
    fun showDialog(activity: Activity, title: String, message: String, listener: BaseSingleDialog.ConfirmClickListener) {
        if (basSingleDialog == null) {
            basSingleDialog = BaseSingleDialog(activity)
        }
        basSingleDialog?.let { /*设置弹框点击周围不予消失*/
            it.setCanceledOnTouchOutside(false)
            it.setCancelable(false)
            /*设置弹框背景*/
            it.window.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_white))
            it.setContent(message)
                .setTitle(title)
                .setOnConfirmClickListener(object :ConfirmClickListener{
                    override fun sure() {
                        listener.sure()
                        it.dismiss()
                    }

                }).show() }

    }
}