package cn.catherine.token.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import cn.catherine.token.BuildConfig
import cn.catherine.token.R
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.StringTool
import kotlinx.android.synthetic.main.layout_loading_dailog.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 16:34
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.view.dialog
+--------------+---------------------------------
+ description  +   自定義Dialog：加载进度的显示框
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseLoadingDialog : Dialog, DialogInterface {
    private val TAG = BaseLoadingDialog::class.java.simpleName

    private lateinit var dlg: Dialog
    internal lateinit var context: Context

    private val mDialogView: View by lazy { View.inflate(context, R.layout.layout_loading_dailog, null) }

    private var cancelAble: Boolean = false

    private var msg: String? = null
    private lateinit var hyperspaceJumpAnimation: Animation

    constructor(context: Context) : super(context)
    constructor(context: Context, msg: String) : super(context, R.style.dialog_loading)

    fun setProgressBarColor(color: Int) {
        val colorStateList = ColorStateList.valueOf(color)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pb_loading.indeterminateTintList = colorStateList
        }
    }

    private fun init(context: Context) {
        dlg = this
        this.context = context
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
            context, R.anim.loading_animation
        )
        cancelAble = true
        val runnable = Runnable { this.initView() }
        runnable.run()
    }

    private fun init(context: Context, msg: String) {
        dlg = this
        this.context = context
        this.msg = msg
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
            context, R.anim.loading_animation
        )
        val runnable = Runnable { this.initView() }
        runnable.run()
    }

    fun initView() {
        setContentView(mDialogView)
        cancelAble = false
        mDialogView.setOnClickListener(null)
        if (!StringTool.isEmpty(msg)) {
            tip_text_view.visibility = View.VISIBLE
            tip_text_view.text = msg// 设置加载信息
        } else {
            tip_text_view.visibility = View.GONE
        }

    }

    fun message(msg: String) {
        if (!StringTool.isEmpty(msg)) {
            tip_text_view.visibility = View.VISIBLE
            tip_text_view.text = msg// 设置加载信息
        } else {
            tip_text_view.visibility = View.GONE
        }
    }

    fun isCancelableOnTouchOutside(cancelable: Boolean): BaseLoadingDialog {
        this.cancelAble = cancelable
        if (BuildConfig.DEBUG)
            this.setCanceledOnTouchOutside(cancelAble)
        else
            this.setCanceledOnTouchOutside(cancelable)
        return this
    }

    fun isCancelable(cancelable: Boolean): BaseLoadingDialog {
        this.cancelAble = cancelable
        if (BuildConfig.DEBUG)
            this.setCancelable(cancelAble)
        else
            this.setCancelable(cancelable)
        return this
    }


    override fun show() {
        if (isShowing) {
            return
        }
        super.show()
    }

    override fun dismiss() {
        if (isShowing) {
            try {
                super.dismiss()
            } catch (e: Exception) {
                LogTool.d(TAG, e.message)
            }

        }
    }

}