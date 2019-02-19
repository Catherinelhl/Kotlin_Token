package cn.catherine.token.base

import android.support.multidex.MultiDexApplication
import com.sjtu.yifei.route.Routerfit

/**
 *
 * @since 2019/2/19
 *
 * @author catherine.brainwilliam
 *
 * @version
 *
 */
object BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        //初始化RouterFit SDK
        Routerfit.init(this)
    }
}