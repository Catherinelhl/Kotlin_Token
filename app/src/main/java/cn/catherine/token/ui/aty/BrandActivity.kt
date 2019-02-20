package cn.catherine.token.ui.aty

import android.os.Bundle
import cn.catherine.token.R
import cn.catherine.token.base.BaseActivity
import cn.catherine.token.constant.Constants
import cn.catherine.token.listener.ObservableTimerListener
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.time.ObservableTimerTool
import java.util.concurrent.TimeUnit

/**
 *
 * @since 2019/2/19
 *
 * @author catherine.brainwilliam
 *
 * @version
 *
 */
class BrandActivity : BaseActivity() {
    val TAG: String = BrandActivity::class.java.simpleName
    override fun getArgs(bundle: Bundle) {

    }

    override fun getLayoutRes(): Int = R.layout.activity_brand

    override fun initViews() {
        //显示2s，然后跳转到登录页面
        ObservableTimerTool.countDownTimerBySetTime(Constants.Time.STAY_BRAND_ACTIVITY, TimeUnit.SECONDS,
            object : ObservableTimerListener {
                override fun timeUp() {
                    //跳转到登录页面
                    val bundle = Bundle()
                    bundle.putString(Constants.KeyMaps.From, Constants.ValueMaps.FROM_BRAND)
                    intentToActivity(bundle, MainActivity::class.java, true)
                }
            })

    }

    override fun initListener() {
    }
}