package cn.catherine.token.http.tcp

import android.os.Looper
import cn.catherine.token.constant.Constants
import cn.catherine.token.listener.TCPRequestListener
import cn.catherine.token.vo.ClientIpInfoVO
import java.net.Socket

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 11:22
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.http.tcp
+--------------+---------------------------------
+ description  +   TCP：開啟Socket以及和服務器建立TCP連接的數據讀取
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object TCPThread {


    private val TAG = TCPThread::class.java.simpleName

    /*向服务器TCP发送的数据*/
    private var writeStr: String? = null
    /*是否存活*/
    @Volatile
    var keepAlive = true
    /*建立連結的socket*/
    @Volatile
    var buildSocket: Socket? = null
    /*得到当前需要去签章的交易区块 */
    private var currentSendVO = null
    /*监听TCP的一些返回，通知界面作出改动 */
    private var tcpRequestListener: TCPRequestListener? = null
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
}