package cn.catherine.token.base

import android.content.Context
import android.support.multidex.MultiDexApplication
import android.util.DisplayMetrics
import android.view.WindowManager
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.LogTool

/**
 *
 * @since 2019/2/19
 *
 * @author catherine.brainwilliam
 *
 * @version
 *
 */
open class BaseApplication : MultiDexApplication() {


    companion object {
        val TAG: String = BaseApplication::class.java.simpleName
        var instance: BaseApplication? = null
    }

    /*屏幕的寬*/
    private var screenWidth: Int = 0
    /*屏幕的高*/
    private var screenHeight: Int = 0


    /**
     * 得到当前的上下文
     * */

    fun context(): Context = instance!!.applicationContext

    override fun onCreate() {
        super.onCreate()
        instance = this
        getScreenMeasure()
        //初始化RouterFit SDK
//        Routerfit.init(this)
    }

    /**
     * 得到当前屏幕的尺寸
     */
    private fun getScreenMeasure() {
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
            LogTool.d(TAG, MessageConstants.DEVICE_INFO + info)
        }
    }

    /**
     * 得到DisplayMetrics
     */
    private fun getDisplayMetrics(): DisplayMetrics? {
        val displayMetrics = DisplayMetrics()
        val windowManager = context().getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        windowManager ?: return null
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }
}