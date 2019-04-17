package cn.catherine.token.http.tcp

import android.os.Looper
import cn.catherine.token.bean.HeartBeatBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.gson.gsonTypeAdapter.TransactionChainVOTypeAdapter
import cn.catherine.token.http.requester.HttpIntervalRequester
import cn.catherine.token.http.requester.MasterRequester
import cn.catherine.token.listener.HttpASYNTCPResponseListener
import cn.catherine.token.listener.ObservableTimerListener
import cn.catherine.token.listener.SocketConnectReadListener
import cn.catherine.token.listener.TCPRequestListener
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.decimal.DecimalTool
import cn.catherine.token.tool.ecc.Sha256Tool
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.tool.json.JsonTool
import cn.catherine.token.tool.time.ObservableTimerTool
import cn.catherine.token.vo.*
import com.google.gson.GsonBuilder
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/19 15:06
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.http.tcp
+--------------+---------------------------------
+ description  +   和服务器建立链接：socket/TCP,開啟Socket以及和服務器建立TCP連接的數據讀取
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

/**
 *@param writeString 传入当前要写入的数据
 * @param tcpRequestListener 监听TCP的一些返回，通知界面作出改动
 *
 */
class SocketClient(private val writeStr: String, private val tcpRequestListener: TCPRequestListener) : Socket() {


    private val TAG = SocketClient::class.java.simpleName
    private val connectReadThread: ConnectReadThread by lazy {
        ConnectReadThread(this, socketConnectReadListener, observableTimerListener)
    }

    /*建立連結的socket*/
    /*得到当前需要去签章的交易区块 */
    private var currentSendVO = null
    /*存儲當前請求回來的需要簽章的交易區塊，做一個現城池，異步處理*/
//    private val getWalletWaitingToReceiveQueue: Cloneable = LinkedList<TransactionChainVO>()
    /*声明一个参数用来存储更改授权代表的返回状态，默认是「change」*/
    private var changeStatus = Constants.CHANGE
    /*用来停止socket请求,这个比kill()大*/
    private var stopSocket = false
    /*当前重连的次数*/
    private var resetCount: Int = 0
    /*当前TCP连接的SAN地址信息*/
    private var clientIpInfoVO: ClientIpInfoVO? = null
    /*当前连接的网络是否是内网*/
    private var isInternal: Boolean = false
    //    /*得到當前建立長鏈接的Handler*/
//    private var tcpReceiveThread: TCPReceiveThread? = null
    /*得到當前建立的長鏈接的looper*/
    private var TCPReceiveLooper: Looper? = null
    /*存儲當前「Send」之後，自己計算的balance*/
    private var balanceAfterSend = ""
    /*判断当前是否Socket是否已经连接*/
    private var socketIsConnect: Boolean = false
    /*判断当前是否是主动断开，以此来判断是否需要重连*/
    private var activeDisconnect: Boolean = false

    /*当前是否可以重置SAN，默认为可以*/
    var canReset: Boolean = false

    /*提示当前是否提示过资料同步中*/
    private var hadToastBalanceSync: Boolean = false

    /*当前授权的账户代表*/
    private var representativeFromInput: String? = null


    init {
        compareIpInfo()

    }

    /**
     * 比对当前的内外网的IP信息，确定连接网络
     */
    private fun compareIpInfo() {
        //比对当前设备的外网IP与SAN的外网IP
        /*1：得到当前设备的外网IP*/
        val walletExternalIp = GlobalVariableManager.walletExternalIp
        /*2：得到当前服务器返回的可以连接的SAN的内外网IP&Port*/
        clientIpInfoVO = GlobalVariableManager.clientIpInfoVO
        if (clientIpInfoVO == null) {
            tcpRequestListener.getDataException(MessageConstants.socket.CLIENT_INFO_NULL)
            return
        }
        /*3：比对当前的APP的外网IP与服务器返回的外网IP，如果相同， 那么就连接内网，否则连接外网*/
        if (StringTool.equals(walletExternalIp, clientIpInfoVO!!.externalIp)) {
            //连接内网IP&Port
            connectInternalIP()
        } else {
            //连接外网IP&Port
            connectExternalIP()
        }
        /*1:創建socket,并且连接*/
        createSocket()
    }


    /*连接内网IP&Port*/
    private fun connectExternalIP() {
        clientIpInfoVO ?: return
        isInternal = false
        clientIpInfoVO?.let {
            //        LogTool.d(TAG, MessageConstants.socket.CONNECT_EXTERNAL_IP);
            GlobalVariableManager.tcpIp = it.externalIp
            GlobalVariableManager.tcpPort = it.externalPort
            GlobalVariableManager.httpPort = it.rpcPort
        }

    }

    /*连接外网IP&Port*/
    private fun connectInternalIP() {
        isInternal = true
        clientIpInfoVO?.let {
            //        LogTool.d(TAG, MessageConstants.socket.CONNECT_INTERNAL_IP);
            GlobalVariableManager.tcpIp = it.internalIp
            GlobalVariableManager.tcpPort = it.internalPort
            GlobalVariableManager.httpPort = it.internalRpcPort
        }

    }

    /**
     * 創建socket,并且连接
     */
    private fun createSocket() {
        //判断当前的socket是否建立连接，如果当前是建立连接的状态，那么就不需要再进行连接
        //        LogTool.d(TAG, socketIsConnect);
        if (socketIsConnect) {
            return
        }
        socketIsConnect = true
        activeDisconnect = false
        val socketAddress = InetSocketAddress(GlobalVariableManager.tcpIp, GlobalVariableManager.tcpPort)
        LogTool.d(TAG, "step 1:" + MessageConstants.socket.TAG + socketAddress)
        tcpRequestListener.refreshTCPConnectIP(GlobalVariableManager.tcpIp + Constants.HTTP_COLON + GlobalVariableManager.tcpPort)
        try {
            //设置socket连接超时时间，如果是内网的话，那么5s之后重连，如果是外网10s之后重连
            this.connect(
                socketAddress,
                if (isInternal)
                    Constants.Time.INTERNET_TIME_OUT
                else
                    Constants.Time.EXTERNAL_TIME_OUT
            )
            //让其在建立连接的时候保持存活
            this.keepAlive = true
            keepAlive = true
            //开始对当前连接的socket进行数据写入
            LogTool.d(TAG, MessageConstants.socket.BUILD_SOCKET + stopSocket)
            //当前stopSocket为false的时候才能允许连接
            if (!stopSocket) {
                isResetExceedTheLimit()
                resetCount++
                if (this.isConnected) {
                    //发送封包信息
                    writeTOSocket(false)
                    /*2:开启接收线程*/
                    connectReadThread.start()
                }
            }
        } catch (e: Exception) {
            LogTool.e(TAG, e.toString())
            if (e is SocketException) {
                socketIsConnect = false
                createSocket()
            } else {
                resetSAN()
            }
        }

    }

    /*判断重置是否超过限定,重置次数已经5次了，那么让他睡10s，然后继续*/
    private fun isResetExceedTheLimit() {
        if (resetCount >= MessageConstants.socket.RESET_MAX_COUNT) {
            resetCount = 0
            try {
                LogTool.d(TAG, MessageConstants.socket.OVER_FIVE_TIME_TO_RESET)
                Thread.sleep(Constants.Time.sleep10000)
            } catch (e: InterruptedException) {
                LogTool.d(TAG, e.message)
            }

        }
    }

    private val socketConnectReadListener = object : SocketConnectReadListener {
        override fun closeConnectRead() {
        }

        override fun getReadData(responseJson: ResponseJson?) {

            if (responseJson != null) {
                val code = responseJson.code
                /*匹配异常code，如果是3006||3008，则是token过期，需要提示其重新登录*/
                if (JsonTool.isTokenInvalid(code)) {
                    //Redis data not found,need logout
                    LogTool.d(TAG, MessageConstants.socket.STOP_SOCKET_TO_LOGIN)
                    if (!stopSocket) {
                        tcpRequestListener.reLogin()
                        closeSocket(true, "TCPLogout")
                    }
                    break
                }
                if (code != MessageConstants.CODE_200) {
                    tcpRequestListener.getDataException(responseJson.message)
                    LogTool.d(TAG, MessageConstants.socket.CODE_EXCEPTION + responseJson.message)
                }
                val methodName = responseJson.methodName
                if (StringTool.isEmpty(methodName)) {
                    LogTool.d(TAG, MessageConstants.METHOD_NAME_IS_NULL)
                } else {
                    when (methodName) {
                        /*得到最新的余额*/
                        MessageConstants.socket.GET_LATEST_BLOCK_AND_BALANCE_SC -> getLatestBlockAndBalance_SC(
                            responseJson
                        )
                        /*发送*/
                        MessageConstants.socket.GET_SEND_TRANSACTION_DATA_SC -> getSendTransactionData_SC(responseJson)
                        /*签章Receive*/
                        MessageConstants.socket.GET_RECEIVE_TRANSACTION_DATA_SC -> getReceiveTransactionData_SC(
                            responseJson
                        )
                        /*获取余额*/
                        MessageConstants.socket.GET_BALANCE_SC ->
                            /*判断当前code是否是"success":false,"code":2097,"message":"The balance data is synchronizing.","methodName":"getBalance_SC","size":0}*/
                            if (code == MessageConstants.CODE_2097) {
                                //提示"资料同步中"，如果当前提示过一次，那么就不再提示
                                if (!hadToastBalanceSync) {
                                    tcpRequestListener.balanceIsSynchronizing()
                                    hadToastBalanceSync = true
                                }
                            } else {
                                getBalance_SC(responseJson)
                            }
                        /*得到最新的R区块*/
                        MessageConstants.socket.GET_WALLET_WAITING_TO_RECEIVE_BLOCK_SC -> getWalletWaitingToReceiveBlock_SC(
                            responseJson
                        )
                        /*获取最新的Change区块*/
                        MessageConstants.socket.GET_LATEST_CHANGE_BLOCK_SC -> getLatestChangeBlock_SC(responseJson)
                        /*响应Change区块数据*/
                        MessageConstants.socket.GET_CHANGE_TRANSACTION_DATA_SC -> getChangeTransactionData_SC(
                            responseJson
                        )
                        /*成功连接到SAN*/
                        MessageConstants.socket.CONNECTION_SUCCESS_SC -> {
                            LogTool.d(TAG, MessageConstants.socket.CONNECT_SUCCESS)
                            //接收到连接成功的信息，关闭倒数计时
                            ObservableTimerTool.closeCountDownTCPConnectTimer()
                            //开始背景执行获取「余额」和「未签章区块」
                            HttpIntervalRequester.startToHttpIntervalRequest(httpASYNTCPResponseListener)
                            //开始向SAN发送心跳，30s一次
                            ObservableTimerTool.startHeartBeatByIntervalTimer(observableTimerListener)
                        }
                        /*与SAN建立的心跳，如果10s没有收到此心跳，那么就需要重新reset*/
                        MessageConstants.socket.HEARTBEAT_SC ->
                            //取消当前心跳倒计时
                            ObservableTimerTool.closeStartHeartBeatByIntervalTimer()
                        /*需要重置AN*/
                        MessageConstants.socket.CLOSE_SOCKET_SC -> resetSAN()
                        else -> LogTool.d(TAG, MessageConstants.METHOD_NAME_ERROR + methodName)
                    }
                }
            } else {
                LogTool.d(TAG, MessageConstants.RESPONSE_IS_NULL)
            }
        }
    }


    /*获取余额*/
    private fun getBalance_SC(responseJson: ResponseJson) {
        LogTool.d(TAG, "step 2:" + MessageConstants.socket.GET_BALANCE_SC)
        if (responseJson.isSuccess) {
            //            LogTool.d(TAG, MessageConstants.socket.SUCCESS_GET_WALLET_GET_BALANCE);
            if (responseJson.code === MessageConstants.CODE_200) {
                parseWalletVoTOGetBalance(responseJson.walletVO)
            }
        } else {
            LogTool.d(TAG, MessageConstants.socket.FAILURE_GET_WALLET_GET_BALANCE)
        }

    }

    /*解析錢包信息。得到服務器返回的餘額*/
    private fun parseWalletVoTOGetBalance(walletVO: WalletVO?) {
        if (walletVO != null) {
            //判斷當前服務器返回的區塊是否和本地的區塊相對應，如果是，才顯示新獲取的餘額
            val blockService = walletVO.blockService
            if (GlobalVariableManager.blockService.equals(blockService)) {
                val walletBalance = walletVO.walletBalance
                //現在Receive區塊沒有返回餘額了。所以判斷但錢餘額為空，就不用顯示，當然，R區塊返回也不用調用這個方法了
                if (StringTool.notEmpty(walletBalance)) {
                    tcpRequestListener.showWalletBalance(walletBalance)

                }
            }
        } else {
            LogTool.d(TAG, MessageConstants.socket.FAILURE_GET_WALLET_GET_BALANCE)
        }
    }

    /**
     * "取最新的區塊 &wallet餘額"
     *
     * @param responseJson
     */
    fun getLatestBlockAndBalance_SC(responseJson: ResponseJson?) {
        var responseJson = responseJson
        // 置空「發送」之後需要計算得到的餘額值
        balanceAfterSend = MessageConstants.Empty
        LogTool.d(TAG, "step 2:" + MessageConstants.socket.GET_LATEST_BLOCK_AND_BALANCE_SC)
        val gson = GsonBuilder()
            .disableHtmlEscaping()
            // 可能是Send/open区块
            .registerTypeAdapter(TransactionChainVO::class.java, TransactionChainVOTypeAdapter())
            .create()
        val databaseVO = responseJson!!.databaseVO ?: return
        val destinationWallet = BCAASApplication.getDestinationWallet()
        val transactionAmount = BCAASApplication.getTransactionAmount()
        if (StringTool.isEmpty(destinationWallet)) {
            LogTool.d(TAG, MessageConstants.DESTINATION_WALLET_IS_NULL)
            return
        }
        if (StringTool.isEmpty(transactionAmount)) {
            LogTool.d(TAG, MessageConstants.AMOUNT_IS_NULL)
            return
        }
        try {
            LogTool.d(TAG, "transactionAmount:$transactionAmount,destinationWallet:$destinationWallet")
            val walletVO = responseJson.walletVO
            if (walletVO != null) {
                balanceAfterSend =
                    DecimalTool.calculateFirstSubtractSecondValue(walletVO.walletBalance, transactionAmount)
                if (StringTool.equals(balanceAfterSend, MessageConstants.NO_ENOUGH_BALANCE)) {
                    tcpRequestListener.noEnoughBalance()
                    balanceAfterSend = ""
                    return
                }
                parseWalletVoTOGetBalance(walletVO)
                val previousBlockStr = gson.toJson(databaseVO.transactionChainVO)
                LogTool.d(TAG, previousBlockStr)
                val previous = Sha256Tool.doubleSha256ToString(previousBlockStr)
                // 2018/8/22请求AN send请求
                responseJson = HttpTransactionRequester.sendAuthNode(
                    previous,
                    walletVO.blockService,
                    destinationWallet,
                    balanceAfterSend,
                    transactionAmount,
                    walletVO.representative,
                    httpASYNTCPResponseListener
                )

                if (responseJson != null) {
                    val code = responseJson!!.code
                    if (code == MessageConstants.CODE_200) {
                        LogTool.d(TAG, MessageConstants.HTTP_SEND_SUCCESS)
                    } else if (code == MessageConstants.CODE_2002) {
                        tcpRequestListener.sendTransactionFailure(responseJson.message)
                    } else {
                        tcpRequestListener.sendTransactionFailure(MessageConstants.SEND_HTTP_FAILED)
                    }
                }

            } else {
                tcpRequestListener.tcpResponseDataError(MessageConstants.NULL_WALLET)
                return
            }

        } catch (e: Exception) {
            LogTool.e(TAG, e.toString())
            e.printStackTrace()
        }

    }

    /**
     * 处理线程下面需要处理的R区块
     *
     * @param responseJson
     */
    fun getReceiveTransactionData_SC(responseJson: ResponseJson) {
        //关闭当前监听接收成功的计时
        ObservableTimerTool.closeCountDownReceiveBlockResponseTimer()
        val code = responseJson.code
        //如果當前是2028：{"databaseVO":{},"walletVO":{"blockService":"COS"},"success":false,"code":2028,"message":"Transaction already exists.","methodName":"getReceiveTransactionData_SC","size":0}
        if (code == MessageConstants.CODE_200) {
            tcpRequestListener.refreshTransactionRecord()
            //同時本地計算餘額
            calculateAfterReceiveBalance(responseJson)
            //签章返回成功，将当前的send块置空
            currentSendVO = null
            getTransactionVOOfQueue(responseJson, true)
        } else if (code == MessageConstants.CODE_2028) {
            //签章返回成功，将当前的send块置空
            currentSendVO = null
            getTransactionVOOfQueue(responseJson, true)
        } else {
            LogTool.d(TAG, MessageConstants.socket.SIGNATURE_FAILED + responseJson)
        }
    }

    //簽章成功之後，通知更新當前的餘額
    private fun calculateAfterReceiveBalance(responseJson: ResponseJson) {
        val walletVO = responseJson.walletVO
        var blockService: String? = null
        var amount: String? = null
        if (walletVO != null) {
            blockService = walletVO.blockService
        }
        val databaseVO = responseJson.databaseVO
        if (databaseVO != null) {
            val transactionChainVONew = databaseVO.transactionChainVO
            if (transactionChainVONew != null) {
                val `object` = transactionChainVONew.tc
                val objectStr = GsonTool.getGson().toJson(`object`)
                // 如果當前是Receive Block，那麼需要餘額與交易額度相加得到當前需要顯示的金額
                if (JsonTool.isReceiveBlock(objectStr)) {
                    val transactionChainReceiveVO = GsonTool.convert(objectStr, TransactionChainReceiveVO::class.java)
                    if (transactionChainReceiveVO != null) {
                        amount = transactionChainReceiveVO!!.getAmount()
                        if (StringTool.notEmpty(amount)) {
                            val walletBalance = BCAASApplication.getWalletBalance()
                            if (StringTool.notEmpty(walletBalance)) {
                                val newBalance = DecimalTool.calculateFirstAddSecondValue(walletBalance, amount)
                                LogTool.d(TAG, MessageConstants.socket.CALCULATE_AFTER_RECEIVE_BALANCE + newBalance)
                                tcpRequestListener.showWalletBalance(newBalance)
                            }
                        }
                    }
                } else if (JsonTool.isOpenBlock(objectStr)) {
                    //如果當前是Open區塊，則不需要去檢查本地餘額是否是空，直接顯示交易額度
                    val transactionChainOpenVO = GsonTool.convert(objectStr, TransactionChainOpenVO::class.java)
                    if (transactionChainOpenVO != null) {
                        amount = transactionChainOpenVO!!.getAmount()
                        if (StringTool.notEmpty(amount)) {
                            LogTool.d(TAG, MessageConstants.socket.CALCULATE_AFTER_RECEIVE_BALANCE + amount!!)
                            tcpRequestListener.showWalletBalance(amount)
                        }
                    }
                }
            }
        }
        //通知当前界面刷新界面
        if (StringTool.notEmpty(blockService) && StringTool.notEmpty(amount)) {
            tcpRequestListener.showNotification(blockService, amount)
        }
    }


    /**
     * TCP響應「發送」交易的結果返回
     *
     * @param walletResponseJson
     */
    fun getSendTransactionData_SC(walletResponseJson: ResponseJson) {
        if (walletResponseJson.code === MessageConstants.CODE_200) {
            //發送成功，直接顯示當前自己發送錢計算出來的balance，服務器現在不作餘額返回，為了加快返回數據的速度。
            if (StringTool.notEmpty(balanceAfterSend)) {
                LogTool.d(TAG, MessageConstants.socket.BALANCE_AFTER_SEND + balanceAfterSend)
                tcpRequestListener.showWalletBalance(balanceAfterSend)
            }
            tcpRequestListener.sendTransactionSuccess(MessageConstants.socket.TCP_TRANSACTION_SUCCESS)
        } else {
            tcpRequestListener.sendTransactionFailure(MessageConstants.socket.TCP_TRANSACTION_FAILURE + walletResponseJson.message)
        }

    }

    private val observableTimerListener = object : ObservableTimerListener {
        override fun timeUp(from: String) {
            if (StringTool.notEmpty(from)) {
                when (from) {
                    Constants.TimerType.COUNT_DOWN_TCP_CONNECT -> resetSAN()
                    Constants.TimerType.COUNT_DOWN_TCP_HEARTBEAT -> {
                        //向SAN发送心跳信息
                        val heartBeatBean = HeartBeatBean(MessageConstants.socket.HEART_BEAT_CS)
                        val writeStr = GsonTool.string(heartBeatBean)
                        writeTOSocket(true)
                    }
                    Constants.TimerType.COUNT_DOWN_RECEIVE_BLOCK_RESPONSE -> clearQueueAndReceive()
                }
            }
        }
    }


    private fun clearQueueAndReceive() {
        LogTool.e(TAG, MessageConstants.socket.CLEAR_QUEUE_AND_RECEIVE)
        //一分钟倒计时到，没有收到服务器响应
//        //清空当前队列，重新开始签章
//        clearGetReceiveBlockQueue()
//        HttpIntervalRequester.startGetWalletWaitingToReceiveBlockLoop(httpASYNTCPResponseListener)
    }


    private val httpASYNTCPResponseListener = object : HttpASYNTCPResponseListener {

        override fun getLatestChangeBlockSuccess() {

        }

        override fun getLatestChangeBlockFailure(failure: String) {
            // 执行「Change」的情况返回，返回code=0，然后执行「更改失败」
            tcpRequestListener.modifyRepresentativeResult(changeStatus, false, MessageConstants.CODE_0)
        }

        override fun resetSuccess() {
            //连接socket
            tcpRequestListener.resetSuccess()
        }

        override fun resetFailure() {
            LogTool.i(TAG, MessageConstants.socket.DATA_ACQUISITION_ERROR)
        }

        override fun logout() {
            tcpRequestListener.reLogin()
            closeSocket(true, "TCPLogoutListener")
        }

        override fun sendFailure() {
            tcpRequestListener.sendTransactionFailure(MessageConstants.Empty)

        }

        override fun canReset() {
            canReset = true
        }

        override fun verifySuccess(from: String) {

        }

        override fun verifyFailure(from: String) {

        }
    }

    /*重新连接SAN*/
    private fun resetSAN() {
        //        tcpRequestListener.needUnbindService();
        LogTool.d(TAG, MessageConstants.socket.RESET_AN + stopSocket)
        //当前stopSocket为false的时候才继续重连
        MasterRequester.reset(httpASYNTCPResponseListener, canReset)
        canReset = false
    }


    /**
     * 用于向服务端写入数据
     */
    private fun writeTOSocket(isHeartBeat: Boolean) {
        val printWriter: PrintWriter
        try {
            if (this.isConnected) {
                //向服务器端发送数据
                //                printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), MessageConstants.socket.CHARSET_NAME));
                printWriter = PrintWriter(this.outputStream)
                printWriter.write(writeStr + Constants.CHANGE_LINE)
                printWriter.flush()
                LogTool.d(TAG, MessageConstants.socket.SEND_DATA + writeStr)
            }
        } catch (e: Exception) {
            LogTool.e(TAG, MessageConstants.socket.CONNECT_EXCEPTION)
            e.printStackTrace()
            if (isHeartBeat) {
                closeSocket(false, "writeTOSocket")
            }
            socketIsConnect = false
            createSocket()
        }

    }

    /**
     * 关闭当前socket连接
     *
     * @param isStopSocket 是否停止socket停止
     */
    fun closeSocket(isStopSocket: Boolean, from: String) {
        LogTool.i(TAG, MessageConstants.socket.CLOSE_SOCKET + "form=>" + from + ";activeDisconnect:" + activeDisconnect)
        stopSocket = isStopSocket
        keepAlive = false
        try {
            this.close()
        } catch (e: Exception) {
            LogTool.e(TAG, MessageConstants.socket.EXCEPTION + e.message)
        }

//        //清空授权代表信息
//        setRepresentativeFromInput(MessageConstants.Empty)
//        //清空未签章区块的接收队列
//        clearGetReceiveBlockQueue()
//        //关闭TCP接收读取线程
//        closeTCPReceiveThread()
//        HttpIntervalRequester.disposeRequest(HttpIntervalRequester.getReceiveBlockByIntervalDisposable)
//        HttpIntervalRequester.disposeRequest(HttpIntervalRequester.getBalanceIntervalDisposable)
//        //关闭心跳timer
//        ObservableTimerTool.closeStartHeartBeatByIntervalTimer()
//        //关闭TCP倒计时timer
//        ObservableTimerTool.closeCountDownTCPConnectTimer()

    }
}