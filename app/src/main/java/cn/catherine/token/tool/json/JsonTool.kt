package cn.catherine.token.tool.json

import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.RequestJson
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.PreferenceTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.vo.PaginationVO
import cn.catherine.token.vo.RemoteInfoVO
import cn.catherine.token.vo.WalletVO
import org.json.JSONException
import org.json.JSONObject

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 14:53
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.json
+--------------+---------------------------------
+ description  +   工具類：JSON 数据判断
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object JsonTool {
    private val TAG = JsonTool::class.java.simpleName

    fun getString(resource: String, key: String): String {
        return getString(resource, key, MessageConstants.Empty)
    }

    fun getString(resource: String, key: String, value: String): String {
        return when {
            StringTool.isEmpty(resource) -> value
            StringTool.isEmpty(key) -> value
            else -> {
                var jsonObject: JSONObject? = null

                try {
                    jsonObject = JSONObject(resource)
                    if (!jsonObject.has(key)) value else jsonObject.getString(key)
                } catch (var5: JSONException) {
                    value
                }

            }
        }
    }


    /*是否是Open区块*/
    fun isOpenBlock(json: String): Boolean {
        return jsonIsBlockOf(json, Constants.BLOCK_TYPE_OPEN)
    }

    /*是否是Change区块*/
    fun isChangeBlock(json: String): Boolean {
        return jsonIsBlockOf(json, Constants.BLOCK_TYPE_CHANGE)
    }

    /*是否是Receive区块*/
    fun isReceiveBlock(json: String): Boolean {
        return jsonIsBlockOf(json, Constants.BLOCK_TYPE_RECEIVE)

    }

    /*是否是Send区块*/
    fun isSendBlock(json: String): Boolean {
        return jsonIsBlockOf(json, Constants.BLOCK_TYPE_SEND)
    }

    private fun jsonIsBlockOf(json: String, type: String): Boolean {
        return if (StringTool.isEmpty(json)) {
            false
        } else json.contains(Constants.BLOCK_TYPE + type + Constants.BLOCK_TYPE_QUOTATION)

    }


    /**
     * 获取需要请求的数据
     * "{
     * walletVO:{        accessToken : String,
     * blockService : String,
     * walletAddress : String
     * }
     * }"
     */
    fun getRequestJson(): RequestJson? {
        val walletAddress = GlobalVariableManager.getWalletAddress()
        val accessToken = PreferenceTool.getInstance().getString(Constants.Preference.ACCESS_TOKEN)
        val blockService = GlobalVariableManager.blockService
        if (StringTool.isEmpty(walletAddress)
            || StringTool.isEmpty(accessToken)
            || StringTool.isEmpty(blockService)
        ) {
            return null
        }
        val walletVO = WalletVO(walletAddress, blockService, accessToken)
        return RequestJson(walletVO)

    }

    /**
     * 获取需要请求的数据
     *
     * @return "{
     *
     *
     * "walletVO":
     * {
     * "walletAddress": String 錢包地址
     * },
     * "remoteInfoVO":
     * {
     * "realIP": String 外網IP
     * }
     * *}"
     */
    fun getRequestJsonWithRealIp(): RequestJson? {
        val walletAddress = GlobalVariableManager.getWalletAddress()
        val accessToken = PreferenceTool.getInstance().getString(Constants.Preference.ACCESS_TOKEN)
        val blockService = GlobalVariableManager.blockService
        if (StringTool.isEmpty(walletAddress)
            || StringTool.isEmpty(accessToken)
            || StringTool.isEmpty(blockService)
        ) {
            return null
        }
        val walletVO = WalletVO(walletAddress, blockService, accessToken)
        val requestJson = RequestJson(walletVO)
        requestJson.remoteInfoVO = RemoteInfoVO(GlobalVariableManager.walletExternalIp)
        LogTool.d(TAG, "requestJson:$requestJson")
        return requestJson

    }

    /**
     * 获取未签章区块的请求数据
     *
     * @return
     */
    fun getWalletWaitingToReceiveBlockRequestJson(): RequestJson? {
        val requestJson = getRequestJson() ?: return null
        val paginationVO = PaginationVO(GlobalVariableManager.getNextObjectId())
        requestJson.paginationVO = paginationVO
        LogTool.i(TAG, GsonTool.string(requestJson))
        return requestJson

    }

    /**
     * 交易记录是否已经存在
     *
     * @param code
     * @return
     */
    fun isTransactionAlreadyExists(code: Int): Boolean {
        return code == MessageConstants.CODE_2028
    }

    /**
     * token失效
     *
     * @param code
     * @return
     */
    fun isTokenInvalid(code: Int): Boolean {
        return code == MessageConstants.CODE_3006 || code == MessageConstants.CODE_3008
    }

    /**
     * 公钥不匹配
     *
     * @param code
     * @return
     */
    fun isPublicKeyNotMatch(code: Int): Boolean {
        return code == MessageConstants.CODE_2006
    }
}