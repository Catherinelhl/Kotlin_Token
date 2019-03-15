package cn.catherine.token.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import cn.catherine.token.R
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.tool.StringTool
import kotlinx.android.synthetic.main.layout_bcaas_dialog.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 18:07
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.view.dialog
+--------------+---------------------------------
+ description  +    自定義Dialog：雙按鈕提示框
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    //define the second constructor
    constructor(context: Context) : this(context, 0)

    // define a constructor that no params
    constructor() : this(BaseApplication.context)

    private var confirmClickListener: ConfirmClickListener? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bcaas_dialog, null)
        setContentView(view)
        btn_left.setOnClickListener { judgeBtnLeftContentToCallBack() }
        btn_right.setOnClickListener { judgeBtnRightContentToCallBack() }
    }

    fun setLeftText(left: String): BaseDialog {
        if (StringTool.isEmpty(left)) return this
        btn_left.text = left
        return this

    }

    fun setRightText(right: String): BaseDialog {
        if (StringTool.isEmpty(right)) return this
        btn_right.text = right
        return this

    }

    fun setContent(content: String): BaseDialog {
        if (StringTool.isEmpty(content)) return this
        tv_content.text = content
        return this

    }

    fun setTitle(title: String): BaseDialog {
        if (StringTool.isEmpty(title)) return this
        tv_title.text = title
        return this

    }

    /*根据内容来判断*/
    private fun judgeBtnLeftContentToCallBack() {
        if (StringTool.equals(btn_left.text.toString(), context.resources.getString(R.string.cancel))) {
            confirmClickListener?.let { it.cancel() }
        } else {
            confirmClickListener?.let { it.sure() }
        }

    }

    private fun judgeBtnRightContentToCallBack() {

        if (StringTool.equals(btn_right.text.toString(), context.resources.getString(R.string.confirm))) {
            confirmClickListener?.let { it.sure() }
        } else {
            confirmClickListener?.let { it.cancel() }
        }
    }

    fun setOnConfirmClickListener(confirmClickListener: ConfirmClickListener): BaseDialog {
        this.confirmClickListener = confirmClickListener
        return this
    }

    interface ConfirmClickListener {
        fun sure()

        fun cancel()
    }


    var baseDialog: BaseDialog? = null

    /**
     * 显示对话框
     *
     * @param message
     * @param listener
     */
    fun showDialog(activity: Activity, message: String, listener: BaseDialog.ConfirmClickListener) {
        showDialog(
            activity,
            context.resources.getString(R.string.warning),
            context.resources.getString(R.string.confirm),
            context.resources.getString(R.string.cancel),
            message,
            listener
        )
    }

    /**
     * 显示对话框
     *
     * @param title
     * @param left
     * @param right
     * @param message
     * @param listener
     */
    fun showDialog(activity: Activity, title: String, left: String, right: String, message: String, listener: BaseDialog.ConfirmClickListener
    ) {
        if (baseDialog == null) {
            baseDialog = BaseDialog(activity)
        }
        baseDialog?.let {
            /*设置弹框点击周围不予消失*/
            it.setCanceledOnTouchOutside(false)
            /*设置弹框背景*/
            it.window.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_white))
            it.setLeftText(left)
                .setRightText(right)
                .setContent(message)
                .setTitle(title)
                .setOnConfirmClickListener(object : BaseDialog.ConfirmClickListener {
                    override fun sure() {
                        listener.sure()
                        it.dismiss()
                    }

                    override fun cancel() {
                        listener.cancel()
                        it.dismiss()
                    }
                }).show()
        }

    }

}