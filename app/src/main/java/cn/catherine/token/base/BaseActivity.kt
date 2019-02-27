package cn.catherine.token.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.catherine.token.R
import cn.catherine.token.manager.DataGenerationManager
import cn.catherine.token.ui.aty.MainActivity

/**
 *
 * @since 2019/2/19
 *
 * @author catherine.brainwilliam
 *
 * @version
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    private val activity by lazy { this }
    val tag by lazy { activity::class.java.simpleName }
    val context: Context by lazy { applicationContext }
    val dataGenerationManager: DataGenerationManager by lazy { DataGenerationManager() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { getArgs(it) }
        setContentView(getLayoutRes())
        initViews()
        initData()
        initListener()
    }

    abstract fun getArgs(bundle: Bundle)
    abstract fun getLayoutRes(): Int
    abstract fun initViews()
    abstract fun initData()
    abstract fun initListener()

    /**
     * 得到当前的屏幕方向是否是垂直
     */
    fun getScreenDirectionIsPortrait(): Boolean =
        this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    fun intentToActivity(bundle: Bundle?, classTo: Class<*>) {
        this.intentToActivity(bundle, classTo, false)
    }

    fun intentToActivity(bundle: Bundle?, classTo: Class<*>, finishFrom: Boolean) {
        this.intentToActivity(bundle, classTo, false, isClearTask = false)

    }


    private fun intentToActivity(bundle: Bundle?, classTo: Class<*>, finishFrom: Boolean, isClearTask: Boolean) {
        val intent = Intent()
        bundle?.let { intent.putExtras(it) }
        if (isClearTask) {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        intent.setClass(this, classTo)
        startActivity(intent)
        if (finishFrom) {
            this.finish()
        }
        overridePendingTransition(R.anim.slide_in_alpha, R.anim.slide_exit_alpha)
    }
}