package cn.catherine.token.ui.aty

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import cn.catherine.token.BuildConfig
import cn.catherine.token.R
import cn.catherine.token.base.BaseActivity
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.constant.Constants
import cn.catherine.token.manager.AppManager
import cn.catherine.token.manager.SoftKeyBroadManager
import cn.catherine.token.tool.SoftKeyBoardTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.wallet.WalletDBTool
import cn.catherine.token.view.dialog.BaseDialog
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_login.*
import kotlinx.android.synthetic.main.include_edit_text_password.*
import java.util.concurrent.TimeUnit

/*
+
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/20 17:23
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.aty
+--------------+---------------------------------
+ description  +   登录
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
+
*/

class LoginActivity : BaseActivity() {

    override fun getArgs(bundle: Bundle) {

    }

    override fun getLayoutRes(): Int = R.layout.aty_login

    override fun initViews() {
        //添加软键盘监听
        SoftKeyBroadManager(btn_unlock_wallet, tv_import_wallet);
    }

    override fun initData() {
    }

    override fun initListener() {
        ll_login.setOnTouchListener { v, event ->
            SoftKeyBoardTool(activity).hideSoftKeyboard()
            false
        }
        ll_password_key.setOnTouchListener { v, event -> true }
        cb_pwd.setOnCheckedChangeListener { buttonView, isChecked ->
            val text = et_password.text.toString()
            if (StringTool.isEmpty(text)) {
                return@setOnCheckedChangeListener
            }
            et_password.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD//设置当前私钥显示不可见


        }
        val subscribeUnlockWallet = RxView.clicks(btn_unlock_wallet)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe { o ->
                SoftKeyBoardTool(activity).hideSoftKeyboard()
                if (WalletDBTool().existKeystoreInDB()) {
                    val password = et_password.text.toString()
                    if (StringTool.notEmpty(password)) {
                        //TODO
//                        presenter.queryWalletFromDB(password)
                    } else {
                        showToast(getString(R.string.enter_password))
                    }
                } else {
                    noWalletInfo()
                }
            }
        val subscribeCreateWallet = RxView.clicks(tv_create_wallet)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe { o ->
                //1：若客户没有存储钱包信息，直接进入创建钱包页面
                //2：若客户端已经存储了钱包信息，需做如下提示
                if (WalletDBTool().existKeystoreInDB()) {
                    activity?.let {
                        BaseDialog().showBcaasDialog(it, resources.getString(R.string.warning),
                            resources.getString(R.string.confirm),
                            resources.getString(R.string.cancel),
                            getString(R.string.create_wallet_dialog_message), object : BaseDialog.ConfirmClickListener {
                                override fun sure() {
                                    startActivityForResult(
                                        Intent(
                                            BaseApplication.context,
                                            CreateWalletActivity::class.java
                                        ), Constants.REQUEST_CODE_CREATE
                                    )
                                }

                                override fun cancel() {

                                }
                            })
                    }

                } else {
                    startActivityForResult(
                        Intent(BaseApplication.context, CreateWalletActivity::class.java),
                        Constants.REQUEST_CODE_CREATE
                    )
                }
            }
        tv_import_wallet.setOnClickListener { v ->
            //1：若客户没有存储钱包信息，直接进入导入钱包页面
            //2：若客户端已经存储了钱包信息，需做如下提示
            if (WalletDBTool().existKeystoreInDB()) {
                activity?.let {
                    BaseDialog().showBcaasDialog(it,
                        resources.getString(R.string.warning),
                        resources.getString(R.string.confirm),
                        resources.getString(R.string.cancel),
                        resources.getString(R.string.import_wallet_dialog_message),
                        object : BaseDialog.ConfirmClickListener {
                            override fun sure() {
                                startActivityForResult(
                                    Intent(BaseApplication.context, ImportWalletActivity::class.java),
                                    Constants.REQUEST_CODE_IMPORT
                                )
                            }

                            override fun cancel() {

                            }
                        })
                }

            } else {
                startActivityForResult(
                    Intent(BaseApplication.context, ImportWalletActivity::class.java),
                    Constants.REQUEST_CODE_IMPORT
                )
            }
        }
//        tv_version.setOnClickListener(View.OnClickListener {
//            if (BuildConfig.ChangeServer) {
//                if (multipleClickToDo(2)) {
//                    intentToActivity(ChangeServerActivity::class.java)
//
//                }
//            }
//        })
    }


    fun noWalletInfo() {
        showToast(resources.getString(R.string.no_wallet))
    }
}