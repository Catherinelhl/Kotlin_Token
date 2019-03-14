package cn.catherine.token.ui.aty

import android.os.Bundle
import android.view.View
import cn.catherine.token.R
import cn.catherine.token.base.BaseActivity
import cn.catherine.token.constant.Constants
import cn.catherine.token.manager.AppManager
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.manager.SoftKeyBroadManager
import cn.catherine.token.tool.PreferenceTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.regex.RegexTool
import cn.catherine.token.tool.wallet.WalletDBTool
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.aty_set_pwd_for_import_wallet.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/1 14:52
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.aty
+--------------+---------------------------------
+ description  +    Activity：「導入錢包」二級頁面，为新导入的钱包设置密码
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SetPasswordForImportWalletActivity : BaseActivity() {
    override fun getArgs(bundle: Bundle) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_set_pwd_for_import_wallet

    override fun initViews() {

        tv_title.text = resources.getString(R.string.import_wallet)
//        pket_pwd.setOnPasswordWatchListener(passwordWatcherListener)
//        pket_confirm_pwd.setOnPasswordWatchListener(passwordConfirmWatcherListener)
        SoftKeyBroadManager(ll_set_pwd_for_import_wallet, v_space)
        ib_back.visibility = View.VISIBLE
    }

    override fun initData() {
    }

    override fun initListener() {

        ll_set_pwd_for_import_wallet.setOnTouchListener { v, event ->
            hideSoftKeyboard()
            false
        }
        ll_content.setOnTouchListener { v, event -> true }
        val subscribeSure = RxView.clicks(btn_sure)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe { o ->
                hideSoftKeyboard()
                val password = pket_pwd.getPassword()
                val passwordConfirm = pket_confirm_pwd.getPassword()
                if (StringTool.isEmpty(password) || StringTool.isEmpty(passwordConfirm)) {
                    showToast(getString(R.string.enter_password))
                } else {
                    if (password!!.length >= Constants.PASSWORD_MIN_LENGTH
                        && passwordConfirm!!.length >= Constants.PASSWORD_MIN_LENGTH) {
                        if (RegexTool.isCharacter(password) && RegexTool.isCharacter(passwordConfirm)) {
                            if (StringTool.equals(password, passwordConfirm)) {
                                PreferenceTool.getInstance().saveString(Constants.Preference.PASSWORD, password)
                                WalletDBTool().insertWalletInDB(GlobalVariableManager.walletBean)
                                AppManager(activity).setResult(false)
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
        val subscribeBack = RxView.clicks(ib_back)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe { o -> AppManager(activity).setResult(true) }
    }

//    private val passwordWatcherListener = { password ->
//        val passwordConfirm = pket_confirm_pwd.getPassword()
//        if (StringTool.equals(password, passwordConfirm)) {
//            hideSoftKeyboard()
//        }
//    }
//    private val passwordConfirmWatcherListener = { passwordConfirm ->
//        val password = pket_pwd.getPassword()
//        if (StringTool.equals(password, passwordConfirm)) {
//            hideSoftKeyboard()
//        }

//}
}