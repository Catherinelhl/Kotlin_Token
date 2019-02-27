package cn.catherine.token.manager

import cn.catherine.token.bean.WalletBean
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.StringTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/27 09:35
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.app
+--------------+---------------------------------
+ description  +   存储当前需要存储的全局变量
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class GlobalVariableManager {

    companion object {
        /*当前登錄的钱包信息*/
        var walletBean: WalletBean? = null
        /*当前账户的币种*/
        var blockService: String? = null
        /*当前设备的外网IP，由服务器返回*/
         var walletExternalIp: String? = null
        /*當前請求R區塊的分頁信息*/
        private var nextObjectId: String? = null

        fun getNextObjectId(): String? {
            return if (StringTool.isEmpty(nextObjectId) || StringTool.equals(
                    nextObjectId,
                    MessageConstants.NEXT_PAGE_IS_EMPTY
                )
            ) {
                //默認第一次穿空字符串
                MessageConstants.Empty
            } else nextObjectId
        }

        fun setNextObjectId(nextObjectId: String) {
            Companion.nextObjectId = nextObjectId
        }
        fun getWalletAddress(): String? {
            walletBean?.run {
                return@run walletBean!!.address
            }
            return null
        }
    }
}