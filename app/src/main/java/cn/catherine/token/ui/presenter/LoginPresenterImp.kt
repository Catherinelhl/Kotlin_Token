package cn.catherine.token.ui.presenter

import cn.catherine.token.base.BaseApplication
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.RequestJson
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.http.requester.BaseHttpRequester
import cn.catherine.token.http.requester.MasterRequester
import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.catherine.token.listener.GetMyIpInfoListener
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.*
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.tool.wallet.WalletDBTool
import cn.catherine.token.ui.constract.LoginContracts
import cn.catherine.token.vo.RemoteInfoVO
import cn.catherine.token.vo.VersionVO
import cn.catherine.token.vo.WalletVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午5:06
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.presenter
+--------------+---------------------------------
+ description  +  Presenter：「Login」界面需要的數據獲取&處理
+ * 1：查询当前本地数据库，如果没有钱包数据，代表没有可解锁的钱包，提示用户创建钱包/导入钱包
+ * 2：如果当前有钱包数据，那么就进行网络请求，进行「登入」的操作，拿到返回的数据
+ * 3：得到钱包登入「accessToken」，存储到当前用户下，然後拿「accessToken」進行「verify」操作，以此来請求判断是否需要重新「登入」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class LoginPresenterImp(private val view: LoginContracts.View) : LoginContracts.Presenter {

    private val TAG = LoginPresenterImp::class.java.simpleName
    private val baseHttpRequester: BaseHttpRequester by lazy { BaseHttpRequester() }

    override fun queryWalletFromDB(password: String) {
        //1：查询当前数据库数据,得到Keystore
        val keyStore = WalletDBTool().queryKeyStore()
        if (StringTool.isEmpty(keyStore)) {
            view.noWalletInfo()
        } else {
            //2：解析当前KeyStore，然后得到钱包信息
            val walletBean = WalletDBTool().parseKeystore(keyStore!!)
            LogTool.d(TAG, PreferenceTool.getInstance().getString(Constants.Preference.PASSWORD))
            //3：比对当前密码是否正确
            if (StringTool.equals(PreferenceTool.getInstance().getString(Constants.Preference.PASSWORD), password)) {
                //4：判断当前的钱包地址是否为空
                val walletAddress = walletBean!!.address
                if (StringTool.isEmpty(walletAddress)) {
                    LogTool.d(MessageConstants.WALLET_DATA_FAILURE)
                    view.noWalletInfo()
                } else {
                    //4:存储当前钱包信息
                    GlobalVariableManager.walletBean = walletBean
                    //5：开始「登入」
                    getRealIpForLoginRequest()
                }
            } else {
                view.passwordError()
            }
        }
    }

    override fun getRealIpForLoginRequest() {
        LogTool.d(TAG, MessageConstants.TO_LOGIN)
        view.showLoading()
        if (!BaseApplication.realNet) {
            view.noNetWork()
            view.hideLoading()
            return
        }
        //1:获取当前client端的ip信息，然后在「getMyIpInfoListener」回调里面判断是否需要继续登录
        MasterRequester.getMyIpInfo(getMyIpInfoListener)
    }


    //2:根据回调判断当前是否需要继续登录
    private val getMyIpInfoListener = object : GetMyIpInfoListener {
        override fun responseGetMyIpInfo(isSuccess: Boolean) {
            view.hideLoading()
            LogTool.d(TAG, MessageConstants.socket.WALLET_EXTERNAL_IP + GlobalVariableManager.walletExternalIp)
            //如果当前返回成功，那么就去登录，如果返回失败，那么就提示登录失败
            if (isSuccess) {
                //call 「login」 request's method
                loginWithRealIp()
            } else {
                //getIp failure,so just alert.
                view.loginFailure()

            }
        }
    }

    /**
     * 开始执行「Login」请求，在成功取得了用户的realIp之后
     */
    private fun loginWithRealIp() {
        //获取当前钱包的地址
        val walletVO = WalletVO(GlobalVariableManager().getWalletAddress())
        //添加当前RealIp参数
        val remoteInfoVO = RemoteInfoVO(GlobalVariableManager.walletExternalIp)
        val requestJson = RequestJson(walletVO)
        requestJson.remoteInfoVO = remoteInfoVO
        GsonTool.logInfo(TAG,MessageConstants.LogInfo.REQUEST_JSON,"loginWithRealIp:",requestJson)
        val body = GsonTool.beanToRequestBody(requestJson)
        baseHttpRequester.login(body, object : Callback<ResponseJson> {
            override fun onResponse(call: Call<ResponseJson>, response: Response<ResponseJson>) {
                val responseJson = response.body()
                if (responseJson == null) {
                    switchServer()
                    return
                }
                if (responseJson.isSuccess) {
                    parseLoginInfo(responseJson.walletVO)
                } else {
                    view.loginFailure()

                }
                view.hideLoading()

            }

            override fun onFailure(call: Call<ResponseJson>, throwable: Throwable) {
                LogTool.d(TAG, throwable.cause)
                switchServer()
            }
        })

    }

    private fun switchServer() {
        //                if (NetWorkTool.connectTimeOut(throwable)) {
        //如果當前是服務器訪問不到或者連接超時，那麼需要重新切換服務器
        LogTool.d(TAG, MessageConstants.CONNECT_TIME_OUT)
        //1：得到新的可用的服务器
        val serverBean = ServerTool.checkAvailableServerToSwitch()
        if (serverBean != null) {
            RetrofitFactory.cleanSFN()
            loginWithRealIp()
        } else {
            ServerTool.needResetServerStatus = true
            view.hideLoading()
            view.loginFailure()
        }
        //                } else {
        //                    view.hideLoading();
        //                    view.loginFailure();
        //                }
    }

    /**
     * 解析登录成功之后的信息
     *
     * @param walletVO
     */
    private fun parseLoginInfo(walletVO: WalletVO?) {
        //得到当前回传的信息，存储当前的accessToken
        if (walletVO == null) {
            view.noWalletInfo()
            return
        }
        val accessToken = walletVO.accessToken
        if (StringTool.isEmpty(accessToken)) {
            view.noWalletInfo()
        } else {
            ServerTool.addServerInfo(walletVO.seedFullNodeList)
            PreferenceTool.getInstance().saveString(Constants.Preference.ACCESS_TOKEN, accessToken)
            view.loginSuccess()
        }
    }

    /**
     *檢查當前是否有需要更新的版本信息
     */
    override fun checkVersionInfo() {
        val versionVO = VersionVO(Constants.ValueMaps.AUTH_KEY)
        val requestJson = RequestJson(versionVO)
        GsonTool.logInfo(TAG,MessageConstants.LogInfo.REQUEST_JSON,"checkVersionInfo:",requestJson)
        val requestBody = GsonTool.beanToRequestBody(requestJson)
        baseHttpRequester.getAndroidVersionInfo(requestBody, object : Callback<ResponseJson> {
            override fun onResponse(call: Call<ResponseJson>, response: Response<ResponseJson>) {
                view.hideLoading()
                if (response != null) {
                    val responseJson = response.body()
                    if (responseJson != null) {
                        if (responseJson!!.isSuccess) {
                            val versionVOList = responseJson!!.getVersionVOList()
                            if (ListTool.noEmpty(versionVOList)) {
                                val versionVONew = versionVOList.get(0)
                                LogTool.d(TAG, MessageConstants.CHECK_UPDATE_SUCCESS)
                                if (versionVONew != null) {
                                    parseVersionInfo(versionVONew)
                                } else {
                                    view.getAndroidVersionInfoFailure()
                                }
                            } else {
                                view.getAndroidVersionInfoFailure()
                            }
                        } else {
                            LogTool.d(TAG, MessageConstants.CHECK_UPDATE_FAILED)
                            view.getAndroidVersionInfoFailure()
                        }
                    }
                } else {
                    view.getAndroidVersionInfoFailure()
                }
            }

            override fun onFailure(call: Call<ResponseJson>, throwable: Throwable) {
                view.hideLoading()
                LogTool.d(TAG, MessageConstants.CHECK_UPDATE_FAILED + throwable.message)
                view.getAndroidVersionInfoFailure()
            }
        })

    }

    /**
     * 将服务器获取的数据与当前数据库的的版本信息进行比对，
     * 查看是否需要更新
     *
     * @param versionVO
     */
    private fun parseVersionInfo(versionVO: VersionVO?) {
        GsonTool.logInfo(TAG,MessageConstants.LogInfo.RESPONSE_JSON,"parseVersionInfo:",versionVO)
        //1:得到服务器返回的更新信息
        val versionName = versionVO!!.version
        //2：比对当前的versionName和服务器返回的Version进行比对
        if (VersionTool().needUpdate(versionName)) {
            LogTool.d(TAG, MessageConstants.NEED_UPDATE)
            val forceUpgrade = versionVO.forceUpgrade
            val updateUrl = versionVO.updateUrl
            val updateSourceUrl = versionVO.updateSourceUrl
            val appStoreUrl = versionVO.appStoreUrl
            val type = versionVO.type
            val modifyTime = versionVO.motifyTime
            val systermTime = versionVO.systemTime
            //3:判断呢是否强制更新
            view.updateVersion(forceUpgrade == 1, appStoreUrl, updateUrl)
        } else {
            LogTool.d(TAG, MessageConstants.NOT_NEED_UPDATE)
        }

    }
}