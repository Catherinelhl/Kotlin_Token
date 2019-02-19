package cn.catherine.token.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

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

    val activity by lazy { this }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { getArgs(it) }
        setContentView(getLayoutRes())
        initViews()
        initListener()
    }

    abstract fun getArgs(bundle: Bundle)
    abstract fun getLayoutRes(): Int
    abstract fun initViews()
    abstract fun initListener()
}