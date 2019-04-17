package cn.catherine.token.tool.time

import cn.catherine.token.base.BaseApplication
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.listener.ObservableTimerListener
import cn.catherine.token.tool.LogTool
import io.reactivex.Observable
import io.reactivex.Observable.timer
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
 * 时间倒计时，定时管理工具
 *
 */
object ObservableTimerTool {
    private val TAG = ObservableTimerTool::class.java.simpleName
    /*倒计时观察者*/
    private var countDownDisposable: Disposable? = null

    /*倒计时观察者*/
    private var countDownTCPConnectDisposable: Disposable? = null


    /*关闭通知显示的观察者*/
    private var countDownNotificationDisposable: Disposable? = null
    /*定时发送心跳观察者*/
    private var heartBeatByIntervalDisposable: Disposable? = null
    /*定时刷新*/
    private var countDownRefreshViewDisposable: Disposable? = null
    /*SAN对C的签章区块响应观察*/
    private var countDownReceiveBlockResponseDisposable: Disposable? = null
    /*定时查询当前的内存信息*/
    private var logDisposable: Disposable? = null

    fun countDownTimerBySetTime(time: Long, timeUnit: TimeUnit, observableTimerListener: ObservableTimerListener?) {
        val subscribe = timer(time, timeUnit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                {
                    //error
                    LogTool.e(TAG, it.toString())
                },
                {
                    //onComplete
                    observableTimerListener?.timeUp(Constants.TimerType.COUNT_DOWN_NOTIFICATION)
                    //close countDown
                    countDownDisposable?.let { it.dispose() }
                },
                {
                    //onSubscribe
                    this.countDownDisposable = it
                }
            )
    }

    /**
     * 开始TCP连接、心跳响应10s倒计时
     */
    fun startCountDownTCPConnectTimer(observableTimerListener: ObservableTimerListener?) {
        //        LogTool.d(TAG, MessageConstants.socket.START_COUNT_DOWN_TIMER);
        closeCountDownTCPConnectTimer()
        if (BaseApplication().tokenIsNull()) {
            //如果当前的token为null，那么就停止所有循环
        } else {
            timer(Constants.Time.COUNT_DOWN_TIME, TimeUnit.SECONDS)
                //                .subscribeOn(Schedulers.io())
                //                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {

                    override fun onSubscribe(d: Disposable) {
                        countDownTCPConnectDisposable = d
                    }

                    override fun onNext(value: Long) {}

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        LogTool.d<String>(TAG, MessageConstants.socket.COUNT_DOWN_OVER)
                        //关闭计时
                        closeCountDownTCPConnectTimer()
                        observableTimerListener?.timeUp(Constants.TimerType.COUNT_DOWN_TCP_CONNECT)
                    }
                })
        }
    }

    /**
     * 关闭TCP连接、心跳响应倒计时
     */
    fun closeCountDownTCPConnectTimer() {
        if (countDownTCPConnectDisposable != null) {
            //            LogTool.i(TAG, MessageConstants.socket.CLOSE_COUNT_DOWN_TIMER);
            countDownTCPConnectDisposable!!.dispose()
        }
    }

    /**
     * 关闭与SAN心跳30秒定时发送器
     */
    fun closeStartHeartBeatByIntervalTimer() {
        if (heartBeatByIntervalDisposable != null) {
            //            LogTool.i(TAG, MessageConstants.socket.CLOSE_START_HEART_BEAT_BY_INTERVAL_TIMER);
            heartBeatByIntervalDisposable!!.dispose()
        }
    }

    /**
     * 开始与SAN心跳30秒定时发送
     *
     * @param observableTimerListener
     */
    fun startHeartBeatByIntervalTimer(observableTimerListener: ObservableTimerListener?) {
        //        LogTool.d(TAG, MessageConstants.socket.START_HEART_BEAT_BY_INTERVAL_TIMER);
        if (BaseApplication().tokenIsNull()) {
            //如果当前的token为null，那么就停止所有循环
            return
        }
        //        int count_time = 30; //总时间
        Observable.interval(0, Constants.Time.HEART_BEAT, TimeUnit.SECONDS)
            //                .take(count_time + 1)//设置总共发送的次数
            //                .map(new io.reactivex.functions.Function<Long, Long>() {
            //                    @Override
            //                    public Long apply(Long aLong) throws Exception {
            //                        //aLong从0开始
            //                        return count_time - aLong;
            //                    }
            //                })
            //                .subscribeOn(Schedulers.io())
            //                .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    heartBeatByIntervalDisposable = d
                }

                override fun onNext(value: Long?) {
                    observableTimerListener?.timeUp(Constants.TimerType.COUNT_DOWN_TCP_HEARTBEAT)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {}
            })
    }

}