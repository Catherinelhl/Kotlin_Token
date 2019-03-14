package cn.catherine.token.ui.presenter

import cn.catherine.token.base.BaseApplication
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.RequestJson
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.http.requester.BaseHttpRequester
import cn.catherine.token.http.retrofit.RetrofitFactory
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.ServerTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.ui.constract.SettingContract
import cn.catherine.token.vo.WalletVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午5:15
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.presenter
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SettingPresenterImp(private val viewInterface: SettingContract.View) : SettingContract.Presenter {

    private val TAG = SettingPresenterImp::class.java.simpleName
    private val baseHttpRequester: BaseHttpRequester by lazy { BaseHttpRequester() }
    override fun logout() {
        if (!BaseApplication.realNet) {
            viewInterface.noNetWork()
            return
        }
        val address = GlobalVariableManager.getWalletAddress()
        if (StringTool.isEmpty(address)) {
            viewInterface.accountError()
            return
        }
        val requestJson = RequestJson()
        val walletVO = WalletVO()
        walletVO.walletAddress = address
        requestJson.walletVO = walletVO
        // logout  暂时没有要求加realIP
//        requestJson.setRemoteInfoVO(new RemoteInfoVO(BCAASApplication.getWalletExternalIp()));
        val body = GsonTool.beanToRequestBody(requestJson)
        //1:请求服务器，「登出」当前账户
        baseHttpRequester.logout(body, object : Callback<ResponseJson> {
            override fun onResponse(call: Call<ResponseJson>, response: Response<ResponseJson>) {
                val walletVoResponseJson = response.body()
                if (walletVoResponseJson == null) {
                    viewInterface.logoutFailure()
                    return
                }
                //2：如果服务器「登出」成功，清除本地存储的token信息
                if (walletVoResponseJson!!.isSuccess()) {
                    viewInterface.logoutSuccess()
                } else {
                    viewInterface.logoutFailure(walletVoResponseJson!!.getMessage())
                }
            }

            override fun onFailure(call: Call<ResponseJson>, throwable: Throwable) {
                //如果當前是服務器訪問不到或者連接超時，那麼需要重新切換服務器
                LogTool.d(TAG, MessageConstants.CONNECT_TIME_OUT)
                //1：得到新的可用的服务器
                val serverBean = ServerTool.checkAvailableServerToSwitch()
                if (serverBean != null) {
                    RetrofitFactory.cleanSFN()
                    logout()
                } else {
                    ServerTool.needResetServerStatus = true
                    viewInterface.logoutFailure(throwable.toString())
                }
            }
        }
        )
    }
}