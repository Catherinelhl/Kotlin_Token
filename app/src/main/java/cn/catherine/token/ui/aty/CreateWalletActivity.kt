package cn.catherine.token.ui.aty

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.catherine.token.R
import cn.catherine.token.base.BaseActivity
import cn.catherine.token.constant.Constants
import cn.catherine.token.listener.PasswordWatcherListener
import cn.catherine.token.manager.AppManager
import cn.catherine.token.manager.SoftKeyBroadManager
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.SoftKeyBoardTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.ecc.WalletTool
import cn.catherine.token.tool.regex.RegexTool
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_create_wallet.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/1 14:42
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.aty
+--------------+---------------------------------
+ description  +   Activity：「创建钱包」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class CreateWalletActivity : BaseActivity() {

    //跳轉「WalletCreatedInfoActivity」的Request code
    private val CREATED_WALLET_INFO_REQUEST_CODE = 0x11

    override fun getArgs(bundle: Bundle) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_create_wallet;

    override fun initViews() {
        ib_back.visibility = View.VISIBLE
        tv_title.text = resources.getString(R.string.create_new_wallet)
        pket_pwd.passwordWatcherListener = passwordWatcherListener
        pket_confirm_pwd.passwordWatcherListener = passwordConfirmWatcherListener
        SoftKeyBroadManager(ll_create_wallet, v_space)
    }

    override fun initData() {
    }

    override fun initListener() {
        ll_create_wallet.setOnTouchListener { v, event ->
            SoftKeyBoardTool(activity).hideSoftKeyboard()
            false
        }
        ll_content.setOnTouchListener { v, event -> true }
        ib_back.setOnClickListener { v -> AppManager(activity).setResult(true) }
        val subscribeSure = RxView.clicks(btn_sure)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe { o ->
                SoftKeyBoardTool(activity).hideSoftKeyboard()
                val password: String? = pket_pwd.getPassword()
                val confirmPwd = pket_confirm_pwd.getPassword()
                if (StringTool.isEmpty(password) || StringTool.isEmpty(confirmPwd)) {
                    showToast(getString(R.string.enter_password))
                } else {
                    if (password!!.length >= Constants.PASSWORD_MIN_LENGTH
                        && confirmPwd!!.length >= Constants.PASSWORD_MIN_LENGTH) {
                        if (RegexTool.isCharacter(password) && RegexTool.isCharacter(confirmPwd)) {
                            if (StringTool.equals(password, confirmPwd)) {
                                val walletBean = WalletTool().createAndSaveWallet(password)
                                if (walletBean != null) {
                                    intentToCheckWalletInfo(walletBean.address, walletBean.privateKey)
                                }
                            } else {
                                showToast(resources.getString(R.string.password_entered_not_match))
                            }

                        } else {
                            showToast(resources.getString(R.string.password_rule_of_length))

                        }

                    } else {
                        showToast(resources.getString(R.string.password_rule_of_length))
                    }
                }
            }
    }


    private val passwordWatcherListener = object : PasswordWatcherListener {
        override fun onComplete(password: String) {
            val passwordConfirm = pket_confirm_pwd.getPassword()
            if (StringTool.equals(password, passwordConfirm)) {
                activity?.let {
                    SoftKeyBoardTool(it).hideSoftKeyboard()
                }
            }
        }

    }
    private val passwordConfirmWatcherListener = object : PasswordWatcherListener {
        override fun onComplete(password: String) {
            val passwordConfirm = pket_pwd.getPassword()
            if (StringTool.equals(password, passwordConfirm)) {
                activity?.let {
                    SoftKeyBoardTool(it).hideSoftKeyboard()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return
            }
            if (requestCode == CREATED_WALLET_INFO_REQUEST_CODE) {
                //跳轉「為導入的錢包設置密碼」返回
                val bundle = data.extras
                if (bundle != null) {
                    val isBack = bundle.getBoolean(Constants.KeyMaps.From)
                    LogTool.d(tag, isBack)
                    if (!isBack) {
                        //否則是點擊「導入」按鈕，那麼應該關閉當前頁面，然後進行登錄
                        AppManager(activity).setResult(false)
                    }
                }
            }
        }
    }


    /**
     * 跳转到显示钱包创建成功之后的信息显示页面
     *
     * @param walletAddress
     * @param privateKey
     */
    private fun intentToCheckWalletInfo(walletAddress: String, privateKey: String) {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putString(Constants.KeyMaps.WALLET_ADDRESS, walletAddress)
        bundle.putString(Constants.KeyMaps.PRIVATE_KEY, privateKey)
        intent.putExtras(bundle)
        intent.setClass(this, WalletCreatedInfoActivity::class.java!!)
        startActivityForResult(intent, CREATED_WALLET_INFO_REQUEST_CODE)
    }
}