package cn.catherine.token.constant

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 14:27
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.constant
+--------------+---------------------------------
+ description  +   定義API Router
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object APIURLConstants {

    /********* seedNode methodName  */
    //当钱包与AuthNode无法通过时调用，取得新的AuthNode Ip资讯
    const val API_SFN_WALLET_RESET_AUTH_NODE_INFO = "/wallet/resetAuthNodeInfo"
    //登入SFN
    const val API_SFN_WALLET_LOGIN = "/wallet/login"
    //登出SFN
    const val API_SFN_WALLET_LOGOUT = "/wallet/logout"
    //验证AccessToken是否可以使用
    const val API_SFN_WALLET_VERIFY = "/wallet/verify"


    /******* API HTTP [sitapp.bcaas.io]  */
    //获取账户已完成交易
    const val API_ACCOUNT_DONE_TC = "/transactionChain/getAccountDoneTc"
    //取得幣種清單
    const val API_GET_BLOCK_SERVICE_LIST = "/publicUnit/getList"
    // 检查Android版本信息
    const val API_GET_ANDROID_VERSION_INFO = "/getAndroidVersionInfo"
    //获取当前wallet的外网IP
    const val API_GET_MY_IP_INFO = "/ipInfo/getMyIPInfo"


    /******* AuthNode HTTP  */
    //获取最新的区块和Wallet余额 AN
    const val API_SAN_WALLET_GET_LATEST_BLOCK_AND_BALANCE = "/wallet/getLatestBlockAndBalance"
    //取得未簽章R區塊的Send區塊 &取最新的R區塊 &wallet餘額
    const val API_SAN_WALLET_GET_WALLET_WAITING_TO_RECEIVE_BLOCK = "/wallet/getWalletWaitingToReceiveBlock"
    /*单独获取余额*/
    const val API_SAN_WALLET_GET_BALANCE = "/wallet/getBalance"
    //获取最新的更換委託人區塊 AN
    const val API_SAN_WALLET_GET_LATEST_CHANGE_BLOCK = "/wallet/getLatestChangeBlock"
    //TC Receive
    const  val API_SAN_WALLET_TRANSACTION_CHAIN_RECEIVE = "/transactionChain/receive"
    //TC Send
    const val API_SAN_WALLET_TRANSACTION_CHAIN_SEND = "/transactionChain/send"
    //TC change AN
    const val API_SAN_WALLET_CHANGE = "/transactionChain/change"


}