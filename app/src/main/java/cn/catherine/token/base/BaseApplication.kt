package cn.catherine.token.base

import android.content.Context
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.support.multidex.MultiDexApplication
import android.util.DisplayMetrics
import android.view.WindowManager
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.db.BaseDBHelper
import cn.catherine.token.event.NetStateChangeEvent
import cn.catherine.token.receiver.NetStateReceiver
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.PreferenceTool
import cn.catherine.token.tool.ServerTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.language.LanguageTool
import com.google.common.eventbus.Subscribe

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/19 14:53
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.json
+--------------+---------------------------------
+ description  +   當前APP's Application,一些整個APP需要公用的常數、變量、SP相關的存儲統一在此類。也包括獲取當前設備的一些尺寸以及硬件信息
+--------------+---------------------------------
+ version      +
+--------------+---------------------------------
*/

class BaseApplication : MultiDexApplication() {
    val tag: String = BaseApplication::class.java.simpleName

    companion object {

        private lateinit var instance: BaseApplication
        /*屏幕的寬*/
        var screenWidth: Int = 0
        /*屏幕的高*/
        var screenHeight: Int = 0

        /*当前上下文*/
        val context: Context by lazy { instance.applicationContext }

        /*当前的语言环境,默认是英文*/
        var isZH: Boolean = false

        /*数据管理库*/
        lateinit var baseDBHelper: BaseDBHelper
        /*判断当前程序是否真的有网*/
        var realNet = true

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        getScreenMeasure()
        createDB()
        registerNetStateReceiver()
        ServerTool.initServerData()
        //初始化RouterFit SDK
//        Routerfit.init(this)
    }

    override fun attachBaseContext(base: Context) {
        //保存系统选择语言
        //        LanguageTool.saveSystemCurrentLanguage(base);
        super.attachBaseContext(LanguageTool.setLocal(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //保存系统选择语言
        LanguageTool.onConfigurationChanged(applicationContext)
    }

    /**
     * 创建存储当前钱包「Keystore」的数据库
     */
    private fun createDB() {
        LogTool.d(tag, MessageConstants.CREATE_DB)
        baseDBHelper = BaseDBHelper(context)

    }

    /*注册网络变化的监听*/
    private fun registerNetStateReceiver() {
        val netStateReceiver = NetStateReceiver()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        this.registerReceiver(netStateReceiver, intentFilter)
    }

    @Subscribe
    fun netChanged(stateChangeEvent: NetStateChangeEvent) {
        realNet = stateChangeEvent.isConnect
    }

    /**
     * 得到当前屏幕的尺寸
     */
    fun getScreenMeasure() {
        val displayMetrics = getDisplayMetrics()
        if (displayMetrics != null) {
            screenWidth = displayMetrics.widthPixels
            screenHeight = displayMetrics.heightPixels
            // 屏幕密度（1.0 / 1.5 / 2.0）
            val density = displayMetrics.density
            // 屏幕密度DPI（160 / 240 / 320）
            val densityDpi = displayMetrics.densityDpi
            val info = (" 设备型号: " + android.os.Build.MODEL
                    + ",\nSDK版本:" + android.os.Build.VERSION.SDK
                    + ",\n系统版本:" + android.os.Build.VERSION.RELEASE + "\n "
                    + MessageConstants.SCREEN_WIDTH + screenWidth
                    + "\n " + MessageConstants.SCREEN_HEIGHT + screenHeight
                    + "\n屏幕密度:  " + density
                    + "\n屏幕密度DPI: " + densityDpi)
            LogTool.d(tag, MessageConstants.DEVICE_INFO + info)
        }
    }

    /**
     * 得到DisplayMetrics
     */
    fun getDisplayMetrics(): DisplayMetrics? {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        windowManager ?: return null
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    /**
     * 返回当前的token是否为空
     *
     * @return
     */
    fun tokenIsNull(): Boolean {
        val accessToken = PreferenceTool.getInstance().getString(Constants.Preference.ACCESS_TOKEN)
        return StringTool.isEmpty(accessToken)
    }


}