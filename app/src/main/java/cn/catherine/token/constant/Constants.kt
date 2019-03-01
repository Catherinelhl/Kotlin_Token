package cn.catherine.token.constant

/**
 *
 * @since 2019/2/20
 *
 * @author catherine.brainwilliam
 *
 * @version
 * 常数类：定义一些数据传输，表明type的key or values
 *
 */
object Constants {
    object KeyMaps {
        const val From: String = "from"
        const val BCAAS_DIR_NAME = "bcaas"
        const val IS_FROM = "isFrom"
        const val CURRENCY_SWITCH = "CURRENCY_SWITCH"//幣種切換;
        const val LANGUAGE_SWITCH = "LANGUAGE_SWITCH"//語言切換;
        const val TAG = "io.bcaas"
        const val DESTINATION_WALLET = "destinationWallet"//接收方的账户地址
        const val SCAN_ADDRESS = "scanAddress"//扫描得到地址信息
        const val ADDRESS_NAME = "addressName"//接收方的账户地址
        const val TRANSACTION_AMOUNT = "transactionAmount"//交易数额

        const val WALLET_ADDRESS = "walletAddress"
        const val PRIVATE_KEY = "privateKey"
        const val ACTIVITY_STATUS = "activityStatus"//activity的状态
        const val COPY_ADDRESS = "address"
        const val blank = " "

    }

    object ValueMaps {
        const val FROM_BRAND: String = "fromBrand"
        const val PRODUCE_KEY_TYPE = "ECC"
        const val BLOCK_TX_TYPE = "Matrix"
        const val DEFAULT_REPRESENTATIVE = "0000000000000000000000000000000000000000000000000000000000000000"//64個零
        const val DEFAULT_PRIVATE_KEY = "****************************************************"
        const val THREE_STAR = "***"
        const val ALIAS_LENGTH = 20
        const val AUTH_KEY = "OrAanNgeDBlRocOkBOcaIasD"
        const val PACKAGE_URL = "/data/data/io.bcaas/"
        const val DOWNLOAD_APK_NAME = "download.apk"
        const val FILE_STUFF = ".txt"
        const val FILE_PROVIDER = ".fileprovider"
        const val EMAIL_TYPE = "*/*"
        const val DEFAULT_PAGINATION = "0"
        const val SUBTRACT = "-"
        const val ADD = "+"
        const val MORNING = "上午"
        const val AFTERROON = "下午"
        const val AM = "AM"
        const val PM = "PM"
        const val BCAAS_FILE_DIR = "bcaas"
        const val ACTIVITY_STATUS_TODO = "TODO" //界面状态还未开始
        const val ACTIVITY_STATUS_TRADING = "TRADING"//界面状态交易正在进行
        var STATUS_DEFAULT = "default"
        var STATUS_SEND = "Send"

        const val FROM_LANGUAGE_SWITCH = "languageSwitch"
        const val FROM_LOGIN = "login"

    }

    object ServerType {
        const val INTERNATIONAL_SIT = "internationalSIT"
        const val INTERNATIONAL_UAT = "internationalUAT"
        const val INTERNATIONAL_PRD = "internationalPRD"

    }
    object Time {
        const val sleep500: Long = 500
        const val sleep400: Long = 400
        const val sleep200: Long = 200
        const val sleep100: Long = 100
        const val sleep1000: Long = 1000
        const val sleep10000: Long = 10000
        const val sleep800: Long = 800
        const val sleep2000: Long = 2000
        const val INTERNET_TIME_OUT: Long = 5 * 60 * 1000//内网连接时间，ms，超时5s之后
        const val EXTERNAL_TIME_OUT: Long = 10 * 60 * 1000//外网连接超时时间，超过10s之后
        const val STAY_AUTH_ACTIVITY: Long = 3//如果当前不用编辑页面，停留在页面的时间3s
        const val STAY_BRAND_ACTIVITY: Long = 2//如果当前不用编辑页面，停留在页面的时间2s

        const val LONG_TIME_OUT: Long = 30//设置超时时间
        const val COUNT_DOWN_TIME: Long = 10//倒数计时
        const val COUNT_DOWN_RECEIVE_BLOCK: Long = 1 //接收「receive」TCP响应结果倒计时
        const val COUNT_DOWN_NOTIFICATION: Long = 2// 通知倒计时
        const val COUNT_DOWN_REPRESENTATIVES: Long = 1//停留当前在「change」授权页面的时间
        const val COUNT_DOWN_GUIDE_TV: Long = 200//TV版Guide頁面需要的倒計時
        const val GET_RECEIVE_BLOCK: Long = 10//获取未签章区块间隔=
        const val GET_BALANCE: Long = 10//获取餘額
        const val HEART_BEAT: Long = 30//TCP  C-S 发送心跳信息间隔
        const val PRINT_LOG: Long = 1 //打印当前设备的内存
        const val TOAST_LONG: Long = 3
        const val TOAST_SHORT: Long = 0
    }


    // MongoDB
    const val MONGODB_KEY_ID = "_id"
    const val MONGODB_KEY_PREVIOUS = "previous"
    const val MONGODB_KEY_BLOCKSERVICE = "blockService"
    const val MONGODB_KEY_BLOCKTYPE = "blockType"
    const val MONGODB_KEY_BLOCKTXTYPE = "blockTxType"
    const val MONGODB_KEY_DESTINATION_WALLET = "destination_wallet"
    const val MONGODB_KEY_SOURCETXHASH = "sourceTxhash"
    const val MONGODB_KEY_BALANCE = "balance"
    const val MONGODB_KEY_AMOUNT = "amount"
    const val MONGODB_KEY_REPRESENTATIVE = "representative"
    const val MONGODB_KEY_RECEIVEAMOUNT = "receiveAmount"
    const val MONGODB_KEY_WALLET = "wallet"
    const val MONGODB_KEY_WORK = "work"
    const val MONGODB_KEY_DATE = "date"
    const val MONGODB_KEY_SIGNATURE = "signature"
    const val MONGODB_KEY_PUBLICKEY = "publicKey"
    const val MONGODB_KEY_PRODUCEKEYTYPE = "produceKeyType"
    const val MONGODB_KEY_TXHASH = "txHash"
    const val MONGODB_KEY_HEIGHT = "height"
    const val MONGODB_KEY_SYSTEMTIME = "systemTime"
    const val MONGODB_KEY_SIGNATURESEND = "signatureSend"
    const val MONGODB_KEY_CURRENCYUNIT = "currencyUnit"
    const val MONGODB_KEY_CIRCULATION = "circulation"
    const val MONGODB_KEY_COINBASE = "coinBase"
    const val MONGODB_KEY_GENEISISBLOCKACCOUNT = "genesisBlockAccount"
    const val MONGODB_KEY_COINBASEACCOUNT = "coinBaseAccount"
    const val MONGODB_KEY_INTERESTRATE = "interestRate"
    const val MONGODB_KEY_CREATETIME = "createTime"
    const val MONGODB_KEY_PUBLICUNIT = "publicUnit"

    // Gson Key
    const val GSON_KEY_VERSIONVO = "versionVO"
    const val GSON_KEY_VERSIONVOLIST = "versionVOList"
    const val GSON_KEY_CLIENTIPINFOVO = "clientIpInfoVO"
    const val GSON_KEY_CLIENTIPINFOVOLIST = "clientIpInfoVOList"
    const val GSON_KEY_DATABASEVOLIST = "databaseVOList"
    const val GSON_KEY_PAGINATIONVO = "paginationVO"
    const val GSON_KEY_PAGINATIONVOLIST = "paginationVOList"
    const val GSON_KEY_WALLETVO = "walletVO"
    const val GSON_KEY_DATABASEVO = "databaseVO"
    const val GSON_KEY_GENESISVO = "genesisVO"
    const val GSON_KEY_TRANSACTIONCHAINVO = "transactionChainVO"
    const val GSON_KEY_ACCESSTOKEN = "accessToken"
    const val GSON_KEY_WALLETADDRESS = "walletAddress"
    const val GSON_KEY_TC = "tc"
    const val GSON_KEY_SUCCESS = "success"
    const val GSON_KEY_CODE = "code"
    const val GSON_KEY_MESSAGE = "message"
    const val GSON_KEY_METHODNAME = "methodName"
    const val GSON_KEY_OBJECTLIST = "objectList"
    const val GSON_KEY_NEXTOBJECTID = "nextObjectId"
    const val GSON_KEY_ID = "_id"
    const val GSON_KEY_AUTH_KEY = "authKey"
    const val GSON_KEY_VERSION = "version"
    const val GSON_KEY_UPDATE_URL = "updateUrl"
    const val GSON_KEY_FORCE_UPGRADE = "forceUpgrade"
    const val GSON_KEY_TYPE = "type"
    const val GSON_KEY_MOTIFY_TIME = "motifyTime"
    const val GSON_KEY_SYSTEM_TIME = "systemTime"


    //RequestCode
    const val REQUEST_CODE_CAMERA_OK = 0x001
    const val REQUEST_CODE_EXTERNAL_STORAGE = 0x002
    const val REQUEST_CODE_INSTALL = 0x003
    const val REQUEST_CODE_SEND_CONFIRM_ACTIVITY = 0x004// 跳转确认密码发送界面
    const val REQUEST_CODE_INSERT_ADDRESS_ACTIVITY = 0x005// 跳转添加地址
    const val REQUEST_CODE_SEND_FILL_IN_ACTIVITY = 0x006// 跳转发送填入信息界面
    const val REQUEST_CODE_IMPORT = 0x007//跳轉至導入的code
    const val REQUEST_CODE_CREATE = 0x008 //跳轉至創建的code


    // 區塊類型
    const val BLOCK_TYPE_OPEN = "Open"
    const val BLOCK_TYPE_SEND = "Send"
    const val BLOCK_TYPE_RECEIVE = "Receive"
    const val BLOCK_TYPE_CHANGE = "Change"
    const val BLOCK_TYPE = "\"blockType\":\""
    const val BLOCK_TYPE_QUOTATION = "\""

    const val CHANGE_LINE = "\r\n"
    const val CHANGE_OPEN = "changeOpen"//change的open区块
    const val CHANGE = "change"//change区块
    const val ENCODE_INGORE_CASE = "identity"//http設置encode忽略
    const val LOCAL_DEFAULT_IP = "0.0.0.0"
    const val HTTP_PREFIX = "http://"
    const val HTTP_COLON = ":"
    const val PROGRESS_MAX = "/100"
    const val NOTIFICATION_CHANNEL_ID = "bcaas"
    const val NOTIFICATION_CHANNEL_NAME = "bcaasWallet"
    const val ACCOUNT_TRANSACTION = "accountTransaction"
    const val TRANSACTION_STR = "transactionStr"

    object From {
        const val INIT_VIEW = "initView"//初始化界面调用
        const val SELECT_CURRENCY = "selectCurrency"//选择币种调用
        const val CHECK_BALANCE = "checkBalance"//查看余额调用
        const val SEND_FRAGMENT = "sendFragment"//发送界面
        const val CHECK_WALLET_INFO = "checkWalletInfo"//查看钱包信息
        const val SEND = "send"//send界面调用
    }

    fun SPLICE_CONVERTER(ip: String?, port: Int): String {
        return HTTP_PREFIX + ip + HTTP_COLON + port
    }

    const val GSON_KEY_MAC_ADDRESS_EXTERNAL_IP = "macAddressExternalIp"
    const val GSON_KEY_EXTERNAL_IP = "externalIp"
    const val GSON_KEY_INTERNAL_IP = "internalIp"
    const val GSON_KEY_CLIENT_TYPE = "clientType"
    const val GSON_KEY_EXTERNAL_PORT = "externalPort"
    const val GSON_KEY_INTERNAL_PORT = "internalPort"
    const val GSON_KEY_VIRTUAL_COIN = "virtualCoin"
    const val GSON_KEY_RPC_PORT = "rpcPort"

    const val GSON_KEY_OBJECT_LIST = "objectList"
    const val GSON_KEY_NEXT_OBJECT_ID = "nextObjectId"
    const val WIF_PRIVATE_KEY = "WIFPrivateKey"
    const val PASSWORD_MIN_LENGTH = 8// 输入密码的最小长度
    const val PASSWORD_MAX_LENGTH = 16// 输入密码的最大长度

    object ServerTypeName {
        const val INTERNATIONAL_SIT = "国际 SIT"
        const val INTERNATIONAL_UAT = "国际 UAT"
        const val INTERNATIONAL_PRD = "国际 PRD"

    }
    /* 國際化語言*/
    object Language {
        const val CN = "CN"//中文簡體
        const val TW = "TW"// 台灣繁體
        const val EN = "EN"//英文
        const val HK = "HK"// 香港繁體
    }

    enum class SettingType {
        //定义一下设置的类型
        CHECK_WALLET_INFO,
        MODIFY_AUTH,
        ADDRESS_MANAGE,
        LANGUAGE_SWITCHING//语言切换
    }
    object BlockService {
        const val BCC = "BCC" //默认的币种
        const val CLOSE = "0"//isStartUp 关闭
        const val OPEN = "1"//isStartUp 开放
    }

    object Preference {

        const val SP_BCAAS_TUTORIAL_PAGE = "BCAASTutorialPage"
        const val PUBLIC_KEY = "publicKey"//公钥
        const val PRIVATE_KEY = "privateKey"//私钥
        const val PASSWORD = "password"//密码
        const val ACCESS_TOKEN = "accessToken"//token 信息
        const val LANGUAGE_TYPE = "languageType"//當前的語言環境

        //引导页面的显示tag值
        const val GUIDE_CREATE = "createWallet"//创建钱包
        const val GUIDE_IMPORT = "importWallet"//导入钱包
        const val GUIDE_UNLOCK = "unlockWallet"//解锁钱包
        const val GUIDE_MAIN_COPY = "mainCopy"//首页复制
        const val GUIDE_MAIN_BALANCE = "mainBalance"//首页余额
        const val GUIDE_MAIN_CURRENCY = "mainCurrency"//首页币种
        const val GUIDE_SEND_ADDRESS = "sendAddress"//发送页面添加地址
        const val GUIDE_SCAN_ADDRESS = "scanAddress"//发送页面扫描地址

        const val GUIDE_TV_LOGIN_SWITCH_LANGUAGE = "loginSwitchLanguage"//TV版login页面提示切换语言
        const val GUIDE_TV_LOGIN_CREATE_WALLET = "loginCreateWalletTV"//TV版login页面提示创建钱包
        const val GUIDE_TV_LOGIN_IMPORT_WALLET = "loginImportWalletTV"//TV版login页面提示导入钱包
        const val GUIDE_TV_LOGIN_UNLOCK_WALLET = "loginUnlockWalletTV"//TV版login页面提示解锁钱包
        const val GUIDE_TV_HOME_CURRENCY = "homeCurrencyTV"//TV版Home页面提示币种切换

    }

    const val RESULT = "result"//扫描二维码返回的结果
    const val RESULT_CODE = 1//发送二维码扫描结果的code
    const val SWITCH_TAB = 3//切换TAB
    const val SWITCH_BLOCK_SERVICE = 4//切换更新区块
    const val SYMBOL_DOT = "\\."
    const val FROM = "From:"

    /*验证*/
    object Verify {

        //verify 接口请求成功
        const val VERIFY_SUCCESS = FROM + "verifySuccess"
        //切换币种
        const val SWITCH_BLOCK_SERVICE = FROM + "switchBlockService"
        //发送交易
        const val SEND_TRANSACTION = FROM + "sendTransaction"
        //背景执行获取余额
        const val GET_BALANCE_LOOP = FROM + "getBalanceLoop"
    }

    /*重置*/
    object Reset {
        //2035 TCP NOT CONNECT
        const val TCP_NOT_CONNECT = FROM + "tcpNotConnect"
        //点击币种
        const val RESET_SAN = FROM + "resetSAN"
        //网络变化
        const val NET_CHANGE = FROM + "netChange"
    }

    /*定时、倒计时管理*/
    object TimerType {
        const val COUNT_DOWN_TCP_CONNECT = "countDownTCPConnect"
        const val COUNT_DOWN_TCP_HEARTBEAT = "countDownTCPHeartBeat"
        const val COUNT_DOWN_NOTIFICATION = "countDownNotification"
        const val COUNT_DOWN_REFRESH_VIEW = "countDownRefreshView"
        const val COUNT_DOWN_RECEIVE_BLOCK_RESPONSE = "countDownReceiveBlockResponse"
    }

    enum class EventSubscriber {
        ALL,
        HOME,
        RECEIVE,
        SEND,
        SETTING,
        CHECK_WALLET_INFO
    }

}