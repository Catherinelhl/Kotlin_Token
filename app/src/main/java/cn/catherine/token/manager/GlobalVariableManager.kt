package cn.catherine.token.manager

import cn.catherine.token.bean.WalletBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.vo.ClientIpInfoVO
import cn.catherine.token.vo.PublicUnitVO

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
        /*当前账户的余额*/
        var walletBalance: String? = null
        /*得到所有的币种*/
        /*當前AN信息*/
        var clientIpInfoVO: ClientIpInfoVO? = null
        /*当前需要交易的金额*/
        lateinit var transactionAmount: String
        /*当前需要交易的地址信息*/
        lateinit var destinationWallet: String
        /*监听当前程序是否保持继续网络请求*/
        var keepHttpRequest: Boolean = false
        /*存储当前要访问的TCP ip & port*/
        var tcpIp: String? = null
        var tcpPort: Int = 0
        var httpPort: Int = 0

        //存儲當前是否登錄，如果登錄，首頁「登錄」按鈕變為「登出」
        var isLogin: Boolean = false
        /*是否是手机版*/
        var isPhone: Boolean = false
        /*定义一个需要显示SAN_IP的变量*/
        var showSANIP: Boolean = false
        var publicUnitVOList: List<PublicUnitVO>? = null
        /* 重置当前余额*/
        fun resetWalletBalance() {
            this.walletBalance = MessageConstants.Empty
        }
        //获取与AN连线的Http请求
        fun getANHttpAddress(): String? {
            return if (StringTool.isEmpty(tcpIp) || tcpPort == 0) {
                null
            } else Constants.SPLICE_CONVERTER(tcpIp, httpPort)
        }
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