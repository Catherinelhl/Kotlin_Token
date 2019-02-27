package cn.catherine.token.base

import android.content.Context
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.support.multidex.MultiDexApplication
import android.util.DisplayMetrics
import android.view.WindowManager
import cn.catherine.token.bean.WalletBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.db.BaseDBHelper
import cn.catherine.token.event.NetStateChangeEvent
import cn.catherine.token.receiver.NetStateReceiver
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.PreferenceTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.language.LanguageTool
import cn.catherine.token.vo.ClientIpInfoVO
import cn.catherine.token.vo.PublicUnitVO
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

    }

    /*当前登錄的钱包信息*/
    private lateinit var walletBean: WalletBean
    /*當前AN信息*/
    private var clientIpInfoVO: ClientIpInfoVO? = null
    /*当前需要交易的金额*/
    private lateinit var transactionAmount: String
    /*当前需要交易的地址信息*/
    private lateinit var destinationWallet: String

    /*当前账户的余额*/
    private var walletBalance: String? = null
    /*监听当前程序是否保持继续网络请求*/
    private var keepHttpRequest: Boolean = false
    /*判断当前程序是否真的有网*/
    private var realNet = true
    /*存储当前要访问的TCP ip & port*/
    private var tcpIp: String? = null
    private var tcpPort: Int = 0
    private var httpPort: Int = 0

    //存儲當前是否登錄，如果登錄，首頁「登錄」按鈕變為「登出」
    private var isLogin: Boolean = false
    /*是否是手机版*/
    private var isPhone: Boolean = false
    /*定义一个需要显示SAN_IP的变量*/
    private var showSANIP: Boolean = false

    override fun onCreate() {
        super.onCreate()
        instance = this
        getScreenMeasure()
        createDB()
        registerNetStateReceiver()
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
        if (stateChangeEvent.isConnect) {
            setRealNet(true)
        } else {
            setRealNet(false)
        }
    }

    private fun setRealNet(realNet: Boolean) {
        this.realNet = realNet
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
            LogTool.d(tag, MessageConstants.DEVICE_INFO + info)
        }
    }

    /**
     * 得到DisplayMetrics
     */
    private fun getDisplayMetrics(): DisplayMetrics? {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        windowManager ?: return null
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }


    fun isIsPhone(): Boolean {
        return isPhone
    }

    fun setIsPhone(isPhone: Boolean) {
        this.isPhone = isPhone
    }

    fun isIsLogin(): Boolean {
        return isLogin
    }

    fun setIsLogin(isLogin: Boolean) {
        this.isLogin = isLogin
    }

    /*得到所有的币种*/
    private var publicUnitVOList: List<PublicUnitVO>? = null


    fun getPublicUnitVOList(): List<PublicUnitVO>? {
        return publicUnitVOList
    }

    fun setPublicUnitVOList(publicUnitVOList: List<PublicUnitVO>) {
        this.publicUnitVOList = publicUnitVOList
    }
    //-------------------------------获取AN相关的参数 start---------------------------

    /*得到新的AN信息*/
    fun setClientIpInfoVO(clientIpInfo: ClientIpInfoVO) {
        LogTool.d(tag, MessageConstants.UPDATE_CLIENT_IP_INFO + clientIpInfo)
        this.clientIpInfoVO = clientIpInfo
    }

    fun getClientIpInfoVO(): ClientIpInfoVO? {
        return clientIpInfoVO

    }

    fun getTcpIp(): String? {
        return tcpIp
    }

    fun setTcpIp(tcpIp: String) {
        this.tcpIp = tcpIp
    }

    fun getTcpPort(): Int {
        return tcpPort
    }

    fun getHttpPort(): Int {
        return httpPort
    }

    fun setHttpPort(httpPort: Int) {
        this.httpPort = httpPort
    }

    fun setTcpPort(tcpPort: Int) {
        this.tcpPort = tcpPort
    }

    //获取与AN连线的Http请求
    fun getANHttpAddress(): String? {
        return if (StringTool.isEmpty(getTcpIp()) || getTcpPort() == 0) {
            null
        } else Constants.SPLICE_CONVERTER(tcpIp, getHttpPort())
    }

    fun setWalletBalance(walletBalance: String) {
        this.walletBalance = walletBalance
    }

    /* 重置当前余额*/
    fun resetWalletBalance() {
        this.walletBalance = MessageConstants.Empty
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

    fun showSANIP(): Boolean {
        return showSANIP
    }

    fun setShowSANIP(showSANIP: Boolean) {
        this.showSANIP = showSANIP
    }

    fun getWalletBean(): WalletBean {
        return if (walletBean == null) {
            WalletBean()
        } else walletBean
    }

    fun setWalletBean(walletBean: WalletBean) {
        LogTool.d(tag, walletBean)
        this.walletBean = walletBean
    }

    //存储当前的交易金额，可能方式不是很好，需要考虑今后换种方式传给send请求
    fun setTransactionAmount(transactionAmount: String) {
        this.transactionAmount = transactionAmount

    }

    fun getTransactionAmount(): String {
        return transactionAmount
    }

    fun getDestinationWallet(): String {
        return destinationWallet
    }

    fun setDestinationWallet(destinationWallet: String) {
        this.destinationWallet = destinationWallet
    }

    fun isKeepHttpRequest(): Boolean {
        return keepHttpRequest
    }

    fun setKeepHttpRequest(keepHttpRequest: Boolean) {
        this.keepHttpRequest = keepHttpRequest
    }

}