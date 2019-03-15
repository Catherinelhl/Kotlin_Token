package cn.catherine.token.http.requester

import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.RequestJson
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.http.tcp.TCPThread
import cn.catherine.token.listener.GetMyIpInfoListener
import cn.catherine.token.listener.HttpASYNTCPResponseListener
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.ServerTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.tool.json.JsonTool
import cn.catherine.token.vo.ClientIpInfoVO
import cn.catherine.token.vo.WalletVO
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 10:57
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.http.requester
+--------------+---------------------------------
+ description  +   執行HTTP請求類：頻繁會用到的HTTP請求，such as:「Verify」、「Reset」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object MasterRequester {
    private val TAG = MasterRequester::class.java.simpleName
    // 存放用户登录验证地址以后返回的ClientIpInfoVO
    private var clientIpInfoVO: ClientIpInfoVO? = null
    private var disposableReset: Disposable? = null
    private var disposableGetRealIp: Disposable? = null
    private var disposableVerify: Disposable? = null

    /**
     * 获取当前Wallet Ip info
     */
    fun getMyIpInfo(getMyIpInfoListener: GetMyIpInfoListener) {
        //判断当前的请求是否存在
        disposeRequest(disposableGetRealIp)
        var walletVO = WalletVO( GlobalVariableManager().getWalletAddress())
        LogTool.d(TAG,walletVO.walletAddress)
        val requestJson = RequestJson(walletVO)
        GsonTool.logInfo(TAG, MessageConstants.LogInfo.REQUEST_JSON, MessageConstants.getMyIpInfo, requestJson)
        val baseHttpRequester = BaseHttpRequester()
        baseHttpRequester.getMyIpInfo(GsonTool.beanToRequestBody(requestJson))
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<ResponseJson> {
                override fun onSubscribe(d: Disposable) {
                    disposableGetRealIp = d
                }

                override fun onNext(responseJson: ResponseJson) {
                    GsonTool.logInfo(
                        TAG,
                        MessageConstants.LogInfo.RESPONSE_JSON,
                        MessageConstants.getMyIpInfo,
                        responseJson
                    )
                    val remoteInfoVO = responseJson.remoteInfoVO
                    if (remoteInfoVO != null) {
                        val walletExternalIp = remoteInfoVO.realIP
                        if (StringTool.notEmpty(walletExternalIp)) {
                            GlobalVariableManager.walletExternalIp = walletExternalIp
                            getMyIpInfoListener.responseGetMyIpInfo(true)
                        } else {
                            getMyIpInfoListener.responseGetMyIpInfo(false)
                        }
                    } else {
                        getMyIpInfoListener.responseGetMyIpInfo(false)
                    }
                }

                override fun onError(e: Throwable) {
                    getMyIpInfoListener.responseGetMyIpInfo(false)
                    disposeRequest(disposableGetRealIp)


                }

                override fun onComplete() {
                    disposeRequest(disposableGetRealIp)

                }
            })
    }

    fun verify(from: String, httpASYNTCPResponseListener: HttpASYNTCPResponseListener) {
        val requestJson = JsonTool.getRequestJsonWithRealIp()
        if (requestJson == null) {
            httpASYNTCPResponseListener.verifyFailure(from)
            return
        }
        //判断当前的请求是否存在
        disposeRequest(disposableVerify)
        val baseHttpRequester = BaseHttpRequester()
        baseHttpRequester.verify(GsonTool.beanToRequestBody(requestJson))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseJson> {

                override fun onSubscribe(d: Disposable) {
                    disposableVerify = d
                }

                override fun onNext(responseJson: ResponseJson) {
                    LogTool.d(TAG, responseJson)
                    httpASYNTCPResponseListener.verifyFailure(from)
                    val code = responseJson.code
                    if (responseJson.isSuccess) {
                        val walletVONew = responseJson.walletVO
                        //当前success的情况有两种
                        if (code == MessageConstants.CODE_200) {
                            //正常，不需要操作
                            httpASYNTCPResponseListener.verifySuccess(from)
                        } else if (code == MessageConstants.CODE_2014) {
                            // 需要替换AN的信息
                            if (walletVONew != null) {
                                val clientIpInfoVO = walletVONew.clientIpInfoVO
                                if (clientIpInfoVO != null) {
                                    GlobalVariableManager.clientIpInfoVO = clientIpInfoVO
                                    //重置AN成功，需要重新連結
                                    reset(httpASYNTCPResponseListener, TCPThread.canReset)
                                }
                            }
                        } else if (code == MessageConstants.CODE_3003
                            || code == MessageConstants.CODE_2034
                            || code == MessageConstants.CODE_2035
                        ) {
                            //重置AN成功，需要重新連結
                            reset(httpASYNTCPResponseListener, TCPThread.canReset)
                        } else {
                            // 异常情况
                            parseHttpExceptionStatus(responseJson, httpASYNTCPResponseListener)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    LogTool.e(TAG, e.toString())
                    httpASYNTCPResponseListener.verifyFailure(from)
                    disposeRequest(disposableVerify)

                }

                override fun onComplete() {
                    disposeRequest(disposableVerify)

                }
            })
    }


    /**
     * 重置AN信息
     */
    fun reset(httpASYNTCPResponseListener: HttpASYNTCPResponseListener, canReset: Boolean) {
        LogTool.d(TAG, MessageConstants.socket.CAN_RESET + canReset)
        if (canReset) {
            //如果当前容器为空，那么就不进行请求
            disposeRequest(disposableGetRealIp)
            //1：先重新请求IP，然后再根据IP重新请求SAN信息
            getMyIpInfo(object : GetMyIpInfoListener {
                override fun responseGetMyIpInfo(isSuccess: Boolean) {
                    //这儿无论请求失败还是成功，本地都会又一个RealIP，所以直接请求即可
                    resetWithRealIp(httpASYNTCPResponseListener)
                }

            })
        }
    }

    private fun resetWithRealIp(httpASYNTCPResponseListener: HttpASYNTCPResponseListener?) {
        val requestJson = JsonTool.getRequestJsonWithRealIp()
        if (requestJson == null) {
            httpASYNTCPResponseListener!!.resetFailure()
            return
        }
        disposeRequest(disposableReset)
        LogTool.d(TAG, MessageConstants.REQUEST_JSON + requestJson)
        val baseHttpRequester = BaseHttpRequester()
        baseHttpRequester.resetAuthNode(GsonTool.beanToRequestBody(requestJson))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseJson> {

                override fun onSubscribe(d: Disposable) {
                    disposableReset = d
                }

                override fun onNext(responseJson: ResponseJson) {
                    if (responseJson.isSuccess) {
                        val walletVOResponse = responseJson.walletVO
                        if (walletVOResponse != null) {
                            clientIpInfoVO = walletVOResponse.clientIpInfoVO
                            if (clientIpInfoVO != null) {
                                if (httpASYNTCPResponseListener != null) {
                                    //获取到新的SAN位置
                                    GlobalVariableManager.clientIpInfoVO = clientIpInfoVO
                                    httpASYNTCPResponseListener.resetSuccess()
                                }
                            }
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    httpASYNTCPResponseListener!!.canReset()
                    //                                                        if (NetWorkTool.connectTimeOut(throwable)) {
                    //如果當前是服務器訪問不到或者連接超時，那麼需要重新切換服務器
                    LogTool.e(TAG, MessageConstants.CONNECT_TIME_OUT)
                    //1：得到新的可用的服务器
                    val serverBean = ServerTool.checkAvailableServerToSwitch()
                    if (serverBean == null) {
                        ServerTool.needResetServerStatus = true
                    }
                    //                                                        } else {
                    //                                                            LogTool.d(TAG, throwable.getMessage());
                    //                                                        }
                    disposeRequest(disposableReset)

                }

                override fun onComplete() {
                    disposeRequest(disposableReset)

                }
            })
    }

    /**
     * 解析当前异常的情况
     *
     * @param responseJson
     */
    fun parseHttpExceptionStatus(
        responseJson: ResponseJson,
        httpASYNTCPResponseListener: HttpASYNTCPResponseListener?
    ) {
        val message = responseJson.message
        LogTool.e(TAG, message)
        val code = responseJson.code
        if (code == MessageConstants.CODE_3003
            || code == MessageConstants.CODE_2012
            // 2012： public static final String ERROR_WALLET_ADDRESS_INVALID = "Wallet address invalid error.";
            || code == MessageConstants.CODE_2026
        ) {
            //  2026：public static final String ERROR_API_ACCOUNT = "Account is empty.";
            httpASYNTCPResponseListener?.resetFailure()
        } else if (JsonTool.isTokenInvalid(code)) {
            httpASYNTCPResponseListener?.logout()
        } else if (code == MessageConstants.CODE_2035 || code == MessageConstants.CODE_2034) {
            //代表TCP没有连接上，这个时候应该停止socket请求，重新请求新的AN
            //            reset();
        } else {
            httpASYNTCPResponseListener?.resetFailure()
        }
    }

    /**
     * 清除 请求
     * 如果当前的请求还没有回来，那么就直接取消，然后重新发起请求
     */
    private fun disposeRequest(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }
}
