package cn.catherine.token.base

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import cn.catherine.token.R
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.listener.PermissionListener
import cn.catherine.token.manager.DataGenerationManager
import cn.catherine.token.service.DownloadService
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.PermissionTool
import cn.catherine.token.tool.SoftKeyBoardTool
import cn.catherine.token.view.dialog.BaseDownloadDialog
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    val activity: Activity by lazy { this }
    val tag by lazy { activity::class.java.simpleName }
    val context: Context by lazy { applicationContext }
    //存储当前需要更新的Android APk路径
    protected var updateAndroidAPKURL: String? = null
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
        this.intentToActivity(bundle, classTo, finishFrom, false)
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

    fun showToast(toastInfo: String) {
        runOnUiThread {
            val toast = Toast.makeText(activity, "", Toast.LENGTH_LONG)
            /*解决小米手机toast自带包名的问题*/
            toast.setText(toastInfo)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    /*隐藏当前键盘*/
    fun hideSoftKeyboard() {
        SoftKeyBoardTool(activity).hideSoftKeyboard()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun httpExceptionStatus(responseJson: ResponseJson) {
    }

    override fun connectFailure() {
    }

    override fun noNetWork() {
    }

}