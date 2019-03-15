package cn.catherine.token.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import cn.catherine.token.R
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.constant.Constants
import kotlinx.android.synthetic.main.download_dialog.*

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
+ description  +    自定義Dialog：显示当前的下载状态
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseDownloadDialog : Dialog {
    private val TAG = BaseDownloadDialog::class.java.simpleName

    // define a constructor that no params
    constructor() : this(BaseApplication.context)

    constructor(context: Context) : super(context) {
        initView()

    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        initView()
    }

    private var baseDownloadDialog: BaseDownloadDialog? = null

    private fun initView() {
        setContentView(R.layout.download_dialog)
    }

    //设置进度
    fun setProgress(progress: Int) {
        if (tv_progress != null) {
            tv_progress!!.text = "$progress+ ${Constants.PROGRESS_MAX}"
        }
        if (down_progress != null) {
            down_progress!!.progress = progress
        }
    }


    fun showDialog(activity: Activity): BaseDownloadDialog {
        hideDownloadDialog()
        baseDownloadDialog = BaseDownloadDialog(activity)

        baseDownloadDialog?.let {
            /*设置弹框点击周围不予消失*/
            it.setCanceledOnTouchOutside(false)
            it.setCancelable(false)
            /*设置弹框背景*/
            //        bcaasDownloadDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white));
            it.show()
        }
        return baseDownloadDialog as BaseDownloadDialog

    }

    fun hideDownloadDialog() {
        baseDownloadDialog?.let {
            it.dismiss()
            it.cancel()
            baseDownloadDialog = null
        }

    }

}