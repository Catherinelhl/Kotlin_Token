package cn.catherine.token.constant

/**
 *
 * @since 2019/2/20
 *
 * @author catherine.brainwilliam
 *
 * @version
 *
 * 常数类：定义一些服务器接口code对应的message以及需要print的message字段
 */
object MessageConstants {
    const val SCREEN_WIDTH: String = "screen width:"
    const val SCREEN_HEIGHT: String = "screen height:"
    const val DEVICE_INFO: String = "Devices info:"
    const val Empty: String = ""

    const val CODE_0 = 0
    const val CODE_200 = 200 // Success
    const val CODE_400 = 400 // Failure
    const val CODE_404 = 404 // Failure
    const val CODE_2001 = 2001
    const val ERROR_LOST_PARAMETERS = "Lost parameters."
    const val CODE_2002 = 2002
    const val ERROR_PARAMETER_FORMAT = "Parameter foramt error."
    const val CODE_2003 = 2003
    const val ERROR_JSON_DECODE = "JSON decode error."
    const val CODE_2004 = 2004
    const val ERROR_NEXT_PAGE_EMPTY = "Next page is empty."
    const val CODE_2005 = 2005
    const val ERROR_SIGNATURE_VERIFY_ERROR = "Signature verify error."
    const val CODE_2006 = 2006
    const val ERROR_PUBLICKEY_NOT_MATCH = "PublicKey not match."
    const val CODE_2007 = 2007
    const val ERROR_TC_BLOCK_HASH_VERIFY = "TC Block hash verify error."
    const val CODE_2008 = 2008
    const val ERROR_BLOCK_PREVIOUS_DATA_VERIFY = "Block previous data verify error."
    const val CODE_2009 = 2009
    const val ERROR_BALANCE_VERIFY = "Balance verify error."
    const val CODE_2010 = 2010
    const val ERROR_AMOUNT_VERIFY = "Amount verify error."
    const val CODE_2011 = 2011
    const val ERROR_CONFIG_LOADING = "Config loading error."
    const val CODE_2012 = 2012//授权人地址错误 http
    const val ERROR_WALLET_ADDRESS_INVALID = "Wallet address invalid error."
    const val CODE_2013 = 2013
    const val ERROR_ADD_TRANSACTION_DATA = "Add Transaction Data error."
    const val CODE_2014 = 2014//為變更AuthNode資訊
    const val SUCCESS_AUTHNODE_IP_INFO_CHANGE = "Authnode clientIpInfo change."
    const val CODE_2015 = 2015
    const val ERROR_API_TTL_LIMIT = "Too many failed attempts. Please try again later."
    const val CODE_2016 = 2016
    const val ERROR_API_SIGNATURESEND = "SignatureSend verify error."
    const val CODE_2026 = 2026
    const val ERROR_API_ACCOUNT = "Account is empty."
    const val CODE_2028 = 2028
    const val TRANSACTION_ALREADY_EXISTS = "Transaction already exists."
    const val CODE_2029 = 2029//token 失效
    const val TOKEY_VERIFY_ERROR = "ToKey verify error."
    const val CODE_2030 = 2030//当前授权人与上一次授权人一致
    const val WALLET_ADDRESS_SAME = "Representative wallet address same."
    const val CODE_2033 = 2033//授权人地址错误
    const val WALLET_ADDRESS_ERROR = "Representative wallet address inconst const valid error."
    const val CODE_2034 = 2034
    const val BLOCK_SERVICE_NOT_SUPPORT = "Block service not support."
    const val CODE_2035 = 2035
    const val TCP_NOT_CONNECT = "TCP not connect"
    const val CODE_2036 = 2036
    const val DESTINATION_WALLET_ADDRESS = "Destination_wallet address same."
    const val CODE_2097 = 2097
    const val THE_BALANCE_DATA_IS_SYNCHRONIZING = "The balance data is synchronizing."
    const val API_SERVER_NOT_RESPONSE = "Api server not response."

    // Database 3xxx
    const val CODE_3001 = 3001
    const val ERROR_REDIS_CONNECTION_POOL = "Redis connection pool error."
    const val CODE_3003 = 3003
    const val ERROR_REDIS_BLOCKSERVICE_AUTHNODE_MAPPING_LIST_NOT_FOUND =
        "Redis BlockService authnode mapping list not found."
    const val CODE_3004 = 3004
    const val ERROR_REDIS_BLOCKSERVICE_FULLNODE_MAPPING_LIST_NOT_FOUND =
        "Redis BlockService fullnode mapping list not found."
    const val CODE_3005 = 3005
    const val ERROR_REDIS_RUNTIME_EXCEPTION = "Redis runtime exception."
    const val CODE_3006 = 3006
    const val ERROR_REDIS_DATA_NOT_FOUND = "Redis data not found."
    const val CODE_3007 = 3007
    const val ERROR_REDIS_DATA_NOT_MAPPING = "Redis data not mapping."
    const val CODE_3008 = 3008
    const val ERROR_REDIS_ACCESS_TOKEN_AUTH_FAIL = "Redis access token auth fail."

    //API 返回
    const val CODE_2025 = 2025//PublicUnit no data.


    // Get LatestBlock And Balance
    const val SUCCESS_GET_LATEST_BLOCK_AND_BALANCE = "Get LatestBlock And Balance Success."
    const val FAILURE_GET_LATEST_BLOCK_AND_BALANCE = "Get LatestBlock And Balance Failure."

    // Get SendBlock
    const val SUCCESS_GET_WALLET_RECEIVE_BLOCK = "Get  Wallet Waiting To Receive Block Success."
    const val FAILURE_GET_WALLET_RECEIVE_BLOCK = "Get Wallet Waiting To Receive Block Failure."

    // Get Balance
    const val SUCCESS_GET_WALLET_GET_BALANCE = "Get  Wallet Balance Success."
    const val FAILURE_GET_WALLET_GET_BALANCE = "Get Wallet Balance Failure."

    //request param jude print out
    const val PREVIOUS_IS_NULL = "previous is null."//previous
    const val AMOUNT_IS_NULL = "amount is null."
    const val DESTINATION_WALLET_IS_NULL = "destinationWallet is null."
    const val RESPONSE_IS_NULL = "response is null."
    const val METHOD_NAME_IS_NULL = "method name is null."
    const val METHOD_NAME_ERROR = "methodName error."
    const val NO_BLOCK_SERVICE = "no block service"
    const val VERIFY_SUCCESS = "Verify success;"
    const val VERIFY_FAILURE = "Verify failure;"
    const val BALANCE = "balance ："
    const val SEND_TRANSACTION_SATE = "Send Transaction:"
    const val HTTP_SEND_SUCCESS = "send http success,wait tcp..."
    const val NULL_WALLET = "http getLatestBlockAndBalance_SC wallet is null"
    const val SEND_HTTP_FAILED = "send http failed"
    const val LOGOUT_SUCCESSFULLY = "logout successfully"
    const val ADDRESS_LIMIT = 100
    const val CHECK_UPDATE_FAILED = "check update failed:"
    const val CHECK_UPDATE_SUCCESS = "check update successfully"
    const val GOOGLE_PLAY_MARKET = "market://details?id="
    const val CREATE_DB = "createDB"
    const val INSERT_KEY_STORE = "step 3:insertKeyStore"
    const val UPDATE_KEY_STORE = "step 3:updateKeyStore"
    const val HAD_WRITE_PERMISSION = "我已经获取读写权限了"
    const val WRITE_PERMISSION_REFUSED = "我被拒绝获取读写权限了"
    const val GET_WALLET_WAITING_TO_RECEIVE_BLOCK = "getWalletWaitingToReceiveBlock"
    const val GET_BALANCE = "getBalance:"
    const val GET_LATEST_BLOCK_AND_BALANCE = "getLatestBlockAndBalance:"
    const val WALLET_INFO = "WalletBean by parse keystore :"
    const val WALLET_CREATE_EXCEPTION = "Use PrivateKey WIFStr Create Exception "
    const val GET_TCP_DATA_EXCEPTION = "获取TCP数据返回code!=200的异常:"
    const val TO_LOGIN = "to login"
    const val BIND_TCP_SERVICE = "bind tcp service"
    const val START_TCP_SERVICE_BY_ALREADY_CONNECTED = "start tcp service by already connected"
    const val SERVICE_DISCONNECTED = "onServiceDisconnected"
    const val ALL_SERVER_INFO = "all server info:"
    const val CONNECT_TIME_OUT = " 连接超时，切換服務器....."
    const val CONNECT_EXCEPTION = "connect exception,need switch server..."
    const val NEW_SFN_SERVER = "Got a new SFN server url:"
    const val WALLET_DATA_FAILURE = "wallet data httpExceptionStatus"
    const val NO_TRANSACTION_RECORD = "noAccountDoneTC"
    const val GET_ACCOUNT_DONE_TC_SUCCESS = "Get Account Transaction Info Success."
    const val NEXT_PAGE_IS_EMPTY = "NextPageIsEmpty"
    const val UPDATE_CLIENT_IP_INFO = "Authnode clientIpInfo change:"
    const val LOADING_MORE = "loading more"

    const val DESTROY = "destroy:"
    const val NO_ENOUGH_BALANCE = "-1"
    const val AMOUNT_EXCEPTION_CODE = "-1"
    const val AMOUNT_EXCEPTION = "amount exception"
    const val RESET_SAN_SUCCESS = "reset san success；"
    const val VERIFY_SUCCESS_AND_RESET_SAN = "verify success and reset san ；"
    const val RESET_SAN_FAILURE = "reset san failure；"
    const val ON_BACK_PRESSED = "onBackPressed"
    const val TV_DEVICE = "TV DEVICE"
    const val NON_TV_DEVICE = "NON TV DEVICE"
    const val CPU_INFO = "CPU info:"
    const val HTTP_EXCEPTION_STATUS = "httpExceptionStatus"
    const val GET_PREVIOUS_MODIFY_REPRESENTATIVE = "getPreviousModifyRepresentative"
    const val NOT_NEED_UPDATE = "not need update version info"
    const val NEED_UPDATE = "need update version info"
    const val DEFAULT_PASSWORD = "aaaaaaa1"
    const val UNBIND_SERVICE = "UNBIND_SERVICE"
    const val SOCKET_HAD_CONNECTED_START_TO_RECEIVE = "[TCP] socket had connected start to receive tcp info+++++"
    const val FINISH_DOWNLOAD = "finish download..."
    const val DOWNLOAD_ID = "download id:"
    const val START_DOWNLOAD_ANDROID_APK = "Start Download Android APK:"
    const val INSTALL_ANDROID_APK = "Install Android APK"
    const val DOWNLOAD_FINISH_RECEIVER = "Download Finish Receiver"
    const val APK_PATH_IS_NULL = "APKPath is null"
    const val CHECK_SIM_STATUS_IS_TV = "checkSIMStatusIsTv"
    const val MANUFACTURER = "manufacturer:"
    const val BRAND = "brand:"
    const val BOARD = "board:"
    const val DEVICE = "device:"
    const val MODEL = "model:"
    const val DISPLAY = "display:"
    const val PRODUCT = "product:"
    const val fingerprint = "fingerPrint:"
    const val startAppSYNCDownload = "start download app inside app"
    const val getMyIpInfo = "getMyIpInfo:"
    const val getAccountDoneTCFailure = "getAccountDoneTCFailure:"


    /*socket 连接会用到的log以及字段*/
    object socket {
        const val TAG = "[TCP] +++++++++++"
        const val KILL = "[TCP] socket closeSocket..."
        const val EXCEPTION = "[TCP] socket close Exception..."
        const val CLOSE_SOCKET = "[TCP] closeSocket:"
        const val SEND_DATA = "[TCP] 发送socket数据："
        const val CONNECT_EXCEPTION = "[TCP] receive connect exception:"
        const val TCP_RESPONSE = "[TCP] step 1: tcp 返回数据: "
        const val TCP_TRANSACTION_SUCCESS = "[TCP] transaction success ."
        const val TCP_TRANSACTION_FAILURE = "[TCP] transaction httpExceptionStatus ."
        const val RESET_AN = "[TCP]  初始化socket失败，重新请求「sfn」resetAN:"
        const val RESET_MAX_COUNT = 5
        //获取最新的余额和区块
        const val GET_LATEST_BLOCK_AND_BALANCE_SC = "getLatestBlockAndBalance_SC"
        //交易发送结果
        const val GET_SEND_TRANSACTION_DATA_SC = "getSendTransactionData_SC"
        // 签章区块结果
        const val GET_RECEIVE_TRANSACTION_DATA_SC = "getReceiveTransactionData_SC"
        // 拿去未签章区块的数据
        const val GET_WALLET_WAITING_TO_RECEIVE_BLOCK_SC = "getWalletWaitingToReceiveBlock_SC"
        //獲取最新委託人區塊
        const val GET_LATEST_CHANGE_BLOCK_SC = "getLatestChangeBlock_SC"
        //更改委託人區塊
        const val GET_CHANGE_TRANSACTION_DATA_SC = "getChangeTransactionData_SC"
        //关闭socket
        const val CLOSE_SOCKET_SC = "closeSocket_SC"
        //成功连接到TCP
        const val CONNECTION_SUCCESS_SC = "connectionSuccess_SC"
        //TCP连接的心跳 S_C
        const val HEARTBEAT_SC = "heartbeat_SC"
        //获取余额
        const val GET_BALANCE_SC = "getBalance_SC"
        const val STOP_SOCKET_TO_LOGIN = "[TCP] stop socket to re-login"
        const val CODE_EXCEPTION = "[TCP] 返回数据CODE不是200，异常信息："
        const val CLIENT_INFO_NULL = "[TCP] Client info must not null"
        const val SIGNATURE = "[TCP] Signature"

        const val SIGNATURE_FAILED = "[TCP] Signature Failed:"
        const val CURRENT_RECEIVE_QUEUE_SIZE = "[TCP] current need receive queue size:"

        // Get Balance
        const val FAILURE_GET_WALLET_GET_BALANCE = "[TCP] Get Wallet Balance Failure."
        const val BALANCE_AFTER_SEND = "[TCP] Balance after 「Send」:"
        const val CALCULATE_AFTER_RECEIVE_BALANCE = "[TCP] calculateAfterReceiveBalance balance:"
        const val OVER_FIVE_TIME_TO_RESET = "[TCP] more than five time to reset"
        const val BUILD_SOCKET = "[TCP] BUILD SOCKET:"
        const val HEART_BEAT_CS = "heartbeat_CS"
        const val CLOSE_TCP_RECEIVE_THREAD = "[TCP] closeTCPReceiveThread"
        const val COUNT_DOWN_OVER = "[TCP] 倒数计时到，没有收到SAN连接成功信息"
        const val CONNECT_SUCCESS = "[TCP] 与SAN成功建立连接，关闭等待倒计时；"
        const val WALLET_EXTERNAL_IP = "[TCP]  当前Wallet的外网IP是:"
        const val CAN_RESET = "[TCP] 当前是否可以ResetSAN:"
        const val DATA_ACQUISITION_ERROR = "[TCP] data acquisition error "

        const val CLEAR_QUEUE_AND_RECEIVE = "[TCP_RECEIVE] clearQueueAndReceive"
        const val TCP_NOT_CONNECT = "2035"
        const val CHARSET_NAME = "UTF-8"
    }


    const val KEYSTORE_IS_NULL = "keystore is null"
    const val CHARSET_FORMAT = "UTF-8"   //字节码格式
    const val HTTP_CONTENT_ENCODING = "Content-Encoding"
    const val INTENT_GOOGLE_PLAY = "intentToGooglePlay:"
    const val CHECK_WRITE_STORAGE_PERMISSION = "check permission"
    const val NOT_FOUND = "not found"
    const val BCAAS_WALLET = "BcaasWallet"
    const val GET_BALANCE_DATA_ERROR = "[GetBalance] Account data exception"
    const val GET_WALLET_WAITING_TO_RECEIVE_BLOCK_DATA_ERROR = "[getWalletWaitingToReceiveBlock] Account data exception"
    const val DATA_ERROR = "Account data exception"
    const val REQUEST_JSON = LogInfo.RESET_TAG + "requestJson:"


    /*日志信息*/
    object LogInfo {
        const val LOGOUT_TAG = "[LogOut] \r"
        const val SERVICE_TAG = "[Service] \r"
        const val VERIFY_TAG = "[Verify] \r"
        const val RESET_TAG = "[Reset]"
        const val REQUEST_JSON = "【RequestJson】"
        const val RESPONSE_JSON = "【ResponseJson】"

    }

}