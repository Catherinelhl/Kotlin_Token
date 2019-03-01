package cn.catherine.token.ui.frg

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.catherine.token.R
import cn.catherine.token.adapter.SettingsAdapter
import cn.catherine.token.base.BaseFragment
import cn.catherine.token.bean.SettingsBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.listener.OnItemSelectListener
import cn.catherine.token.manager.AppManager
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.StringTool
import cn.catherine.token.ui.aty.AddressManagerActivity
import cn.catherine.token.ui.aty.CheckWalletInfoActivity
import cn.catherine.token.ui.aty.LanguageSwitchingActivity
import cn.catherine.token.ui.aty.ModifyAuthorizedRepresentativesActivity
import cn.catherine.token.view.dialog.BaseDialog
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.frg_setting.*
import java.util.*
import java.util.concurrent.TimeUnit

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/27 14:25
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.frg
+--------------+---------------------------------
+ description  +   Fragment：「设置」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SettingFragment : BaseFragment() {
    private lateinit var settingsAdapter: SettingsAdapter

    override fun getLayoutRes(): Int = R.layout.frg_setting

    override fun initViews(view: View) {
        val settingTypes = initSettingTypes()
        settingsAdapter = SettingsAdapter(frgContext, settingTypes)
        rv_setting.layoutManager = LinearLayoutManager(frgContext, LinearLayoutManager.VERTICAL, false)
        rv_setting.adapter = settingsAdapter
    }

    override fun getArgs(bundle: Bundle) {
    }

    override fun initListener() {
        settingsAdapter.setSettingItemSelectListener(object : OnItemSelectListener {
            override fun changeItem(isChange: Boolean) {
            }

            override fun <T> onItemSelect(type: T, from: String) {
                if (type == null) {
                    return
                }
                if (type is SettingsBean) {
                    val settingTypeBean = type as SettingsBean?
                    settingTypeBean?.let {
                        when (it.tag) {
                            Constants.SettingType.CHECK_WALLET_INFO -> intentToActivity(
                                null,
                                CheckWalletInfoActivity::class.java,
                                false
                            )
                            Constants.SettingType.MODIFY_AUTH -> {
                                // 判断当前是否选择了积分
                                if (StringTool.isEmpty(GlobalVariableManager.blockService)) {
                                    showToast(resources.getString(R.string.select_token_please))
                                    return
                                }
                                /*请求授权代表*/
                                /*1：获取最新的授权地址*/
                                intentToActivity(null, ModifyAuthorizedRepresentativesActivity::class.java, false)
                            }
                            Constants.SettingType.ADDRESS_MANAGE -> intentToActivity(
                                null,
                                AddressManagerActivity::class.java,
                                false
                            )
                            Constants.SettingType.LANGUAGE_SWITCHING -> intentToActivity(
                                null,
                                LanguageSwitchingActivity::class.java,
                                false
                            )
                        }
                    }

                }
            }
        })
        val subscribeLogout = RxView.clicks(btnLogout)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe {
                showBaseDialog(
                    resources.getString(R.string.confirm_logout),
                    object : BaseDialog.ConfirmClickListener {
                        override fun sure() {
                            AppManager(activity!!).let {
                                it.cleanAccountData()
                                it.cleanQueueTask()
                                it.intentToLogin()
                            }
                        }

                        override fun cancel() {

                        }
                    })
            }
    }

    fun refreshView() {

    }

    /**
     * 添加页面数据，实则应该写在presenter里面，但是写在里面在切换语言的时候却不会更新数据
     *
     * @return
     */
    private fun initSettingTypes(): List<SettingsBean> {
        val settingTypes = ArrayList<SettingsBean>()
        val settingTypeBean =
            SettingsBean(getString(R.string.view_wallet_info), Constants.SettingType.CHECK_WALLET_INFO)
        val settingTypeBean3 =
            SettingsBean(getString(R.string.change_representatives), Constants.SettingType.MODIFY_AUTH)
        val settingTypeBean4 = SettingsBean(getString(R.string.address_manager), Constants.SettingType.ADDRESS_MANAGE)
        val settingTypeBean5 =
            SettingsBean(getString(R.string.Language_switching), Constants.SettingType.LANGUAGE_SWITCHING)
        settingTypes.add(settingTypeBean)
        settingTypes.add(settingTypeBean3)
        settingTypes.add(settingTypeBean4)
        settingTypes.add(settingTypeBean5)
        return settingTypes

    }
}