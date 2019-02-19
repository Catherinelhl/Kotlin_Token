package cn.catherine.token.tools.time

import cn.catherine.token.listener.ObservableTimerListener
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

/**
 *
 * @since 2019/2/19
 *
 * @author catherine.brainwilliam
 *
 * @version
 * 时间倒计时，定时管理工具
 *
 */
object ObservableTimerTool {
    val TAG = ObservableTimerTool::class.java.simpleName
    fun countDownTimerBySetTime(time: Long, timeUnit: TimeUnit, observableTimerListener: ObservableTimerListener) {
       
    }
}