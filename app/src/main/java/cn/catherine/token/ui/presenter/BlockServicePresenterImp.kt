package cn.catherine.token.ui.presenter

import android.support.design.widget.BaseTransientBottomBar
import android.view.View
import cn.catherine.token.base.BaseApplication
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
import cn.catherine.token.ui.constract.BlockServiceContracts
import cn.catherine.token.vo.PublicUnitVO
import cn.catherine.token.vo.WalletVO
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午4:53
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.presenter
+--------------+---------------------------------
+ description  +   Presenter：獲取「币种清单/getList」數據獲取&處理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BlockServicePresenterImp(private val view: BlockServiceContracts.View) : BlockServiceContracts.Presenter {

    private val TAG = BlockServicePresenterImp::class.java.simpleName
    private val baseHttpRequester: BaseHttpRequester by lazy { BaseHttpRequester() }

    override fun getBlockServiceList(from: String) {
        view.showLoading()
        if (!BaseApplication.realNet) {
            view.hideLoading()
            view.noNetWork()
            return
        }
        val walletVO = WalletVO()
        walletVO.walletAddress = GlobalVariableManager().getWalletAddress()
        val requestJson = RequestJson(walletVO)
        LogTool.d(TAG, requestJson)
        val requestBody = GsonTool.beanToRequestBody(requestJson)
        baseHttpRequester.getBlockServiceList(requestBody, object : BaseCallback<ResponseJson>() {
            override fun onFailure(call: Call<ResponseJson>, t: Throwable) {
                LogTool.e(TAG, t.toString())
                view.hideLoading()
                view.getBlockServicesListFailure(from)

            }

            override fun onSuccess(response: Response<ResponseJson>) {
                view.hideLoading()
                val responseJson = response.body()
                LogTool.d(TAG, responseJson)
                if (responseJson != null) {
                    if (responseJson.isSuccess) {
                        val publicUnitVOList = responseJson!!.getPublicUnitVOList()
                        val publicUnitVOListNew = ArrayList<PublicUnitVO>()
                        if (ListTool.noEmpty(publicUnitVOList)) {
                            for (publicUnitVO in publicUnitVOList) {
                                if (publicUnitVO != null) {
                                    /*isStartUp:0:關閉；1：開放*/
                                    val isStartUp = publicUnitVO!!.isStartup()
                                    if (StringTool.equals(isStartUp, Constants.BlockService.OPEN)) {
                                        publicUnitVOListNew.add(publicUnitVO)
                                    }
                                }
                            }
                            if (ListTool.noEmpty(publicUnitVOListNew)) {
                                //V，然后存储下来
                                GlobalVariableManager.publicUnitVOList = publicUnitVOListNew
                                view.getBlockServicesListSuccess(from, publicUnitVOListNew)
                            } else {
                                view.noBlockServicesList(from)
                            }

                        }
                    } else {
                        val code = responseJson.code
                        if (code == MessageConstants.CODE_2025) {
                            view.noBlockServicesList(from)
                        }
                    }

                } else {
                    view.getBlockServicesListFailure(from)
                }
            }

            override fun onNotFound() {
                super.onNotFound()
                LogTool.e(TAG, MessageConstants.NOT_FOUND)
                view.getBlockServicesListFailure(from)

            }
        })


    }
}