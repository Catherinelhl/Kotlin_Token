package cn.catherine.token.ui.presenter

import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.RequestJson
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.http.callback.BaseCallback
import cn.catherine.token.http.requester.BaseHttpRequester
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.ListTool
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.ui.constract.MainFragmentContracts
import cn.catherine.token.vo.PaginationVO
import cn.catherine.token.vo.WalletVO
import retrofit2.Call
import retrofit2.Response

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午5:12
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.presenter
+--------------+---------------------------------
+ description  +   Presenter：「首頁/mainFragment」界面需要的 數據獲取&處理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class MainFragmentPresenterImp (private val view:MainFragmentContracts.View ): MainFragmentContracts.Presenter{
    private  val TAG=MainFragmentPresenterImp::class.java.simpleName
    private val baseHttpRequester: BaseHttpRequester by lazy { BaseHttpRequester() }

    override fun getAccountDoneTC(nextObjectId: String) {
        val walletVO = WalletVO()
        walletVO.blockService = GlobalVariableManager.blockService
        walletVO.walletAddress = GlobalVariableManager().getWalletAddress()
        val requestJson = RequestJson(walletVO)
        // 默认传0，
        val paginationVO = PaginationVO(nextObjectId)
        requestJson.paginationVO = paginationVO
        LogTool.d(TAG, requestJson)
        val requestBody = GsonTool.beanToRequestBody(requestJson)
        baseHttpRequester.getAccountDoneTC(requestBody, object : BaseCallback<ResponseJson>() {
            override fun onSuccess(response: Response<ResponseJson>) {
                if (response == null) {
                    view.noAccountDoneTC()
                    return
                }
                val responseJson = response.body()
                if (responseJson == null) {
                    view.noAccountDoneTC()
                    return
                }
                LogTool.d(TAG, responseJson)
                if (response.isSuccessful) {
                    //判斷當前是否是同一個幣種，如果不是，就不顯示
                    val walletVOResponse = responseJson.walletVO
                    if (walletVOResponse != null) {
                        val blockService = walletVOResponse.blockService
                        // 判斷當前的幣種信息
                        if (StringTool.equals(blockService, GlobalVariableManager.blockService)) {
                            val paginationVOResponse = responseJson.paginationVO
                            if (paginationVOResponse != null) {
                                val nextObjectId = paginationVOResponse.nextObjectId
                                //不用顯示"點擊顯示更多"
                                view.getNextObjectId(nextObjectId)
                                val objectList = paginationVOResponse.objectList
                                LogTool.d(TAG, nextObjectId + ":" + objectList.size)
                                if (ListTool.isEmpty(objectList)) {
                                    view.noAccountDoneTC()
                                } else {
                                    view.getAccountDoneTCSuccess(objectList)
                                }
                            } else {
                                //交易紀錄信息為空
                                view.noAccountDoneTC()
                            }
                        } else {
                            //幣種信息不相等
                            //重新拿去當前幣種情趣
                            getAccountDoneTC(Constants.ValueMaps.DEFAULT_PAGINATION)
                            //                            view.noAccountDoneTC();
                        }
                    } else {
                        //沒有錢包信息
                        view.noAccountDoneTC()

                    }

                } else {
                    view.httpExceptionStatus(responseJson)
                }
            }

            override fun onNotFound() {
                super.onNotFound()
                view.getAccountDoneTCFailure(MessageConstants.Empty)
            }

            override fun onFailure(call: Call<ResponseJson>, t: Throwable) {
                LogTool.e(TAG,t.toString())
                view.getAccountDoneTCFailure(t.toString())
            }
        }) }
}