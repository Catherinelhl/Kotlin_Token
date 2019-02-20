package cn.catherine.token.tool.time

import android.annotation.SuppressLint
import cn.catherine.token.listener.ObservableTimerListener
import cn.catherine.token.tool.LogTool
import io.reactivex.Observable.timer
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
 * 时间倒计时，定时管理工具
 *
 */
object ObservableTimerTool {
    private val TAG = ObservableTimerTool::class.java.simpleName
    /*倒计时观察者*/
    private var countDownDisposable: Disposable? = null

    @SuppressLint("CheckResult")
    fun countDownTimerBySetTime(time: Long, timeUnit: TimeUnit, observableTimerListener: ObservableTimerListener?) {
        timer(time, timeUnit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //next
                    observableTimerListener?.timeUp()
                    //close countDown
                    countDownDisposable?.let { it.dispose() }
                },
                {
                    //error
                    LogTool.e(TAG, it.toString())
                },
                {
                    //onComplete
                },
                {
                    //onSubscribe
                    this.countDownDisposable = it
                }
            )
    }
}