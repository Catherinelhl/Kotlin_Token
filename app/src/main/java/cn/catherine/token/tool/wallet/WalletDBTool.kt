package cn.catherine.token.tool.wallet

import cn.catherine.token.base.BaseApplication
import cn.catherine.token.bean.WalletBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.PreferenceTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.encryption.AESTool
import cn.catherine.token.tool.json.GsonTool
import com.google.gson.Gson

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 16:32
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.wallet
+--------------+---------------------------------
+ description  +   工具類：钱包数据库操作
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class WalletDBTool {
    private val TAG = WalletDBTool::class.java.simpleName

    /**
     * 将当前钱包存储到数据库
     *
     * @param walletBean
     */
    fun insertWalletInDB(walletBean: WalletBean?) {
        var keyStore: String? = null
        if (walletBean != null) {
            val gson = Gson()
            try {
                //1:对当前的钱包信息进行加密；AES加密钱包字符串，以密码作为向量
                keyStore = AESTool.encodeCBC_128(
                    gson.toJson(walletBean),
                    PreferenceTool.getInstance().getString(Constants.Preference.PASSWORD)!!
                )
                LogTool.d(TAG, "step 1:encode keystore:" + keyStore!!)
            } catch (e: Exception) {
                LogTool.e(TAG, e.toString())
                e.printStackTrace()
            }

        }
        //2：得到当前不为空的keystore，进行数据库操作
        if (StringTool.isEmpty(keyStore)) {
            LogTool.d(TAG, MessageConstants.KEYSTORE_IS_NULL)
            return
        }
        //3：查询当前数据库是否已经存在旧数据,如果没有就插入，否者进行条件查询更新操作，保持数据库数据只有一条
        if (StringTool.isEmpty(queryKeyStore())) {
            LogTool.d(TAG, MessageConstants.INSERT_KEY_STORE)
            BaseApplication.baseDBHelper.insertKeyStore(keyStore!!)
        } else {
            LogTool.d(TAG, MessageConstants.UPDATE_KEY_STORE)
            BaseApplication.baseDBHelper.updateKeyStore(keyStore!!)
        }

    }

    //--------------------------数据库操作---start-----------------------------------------


    /**
     * 查询当前数据库得到存储的Keystore
     *
     * @return
     */
    fun queryKeyStore(): String? {
        val keystore = BaseApplication.baseDBHelper.queryKeyStore()
        LogTool.d(TAG, "step 2:query keystore:$keystore")
        return if (StringTool.isEmpty(keystore)) {
            null
        } else keystore
    }


    /*查询当前数据库中钱包keystore是否已经有数据了*/
    fun existKeystoreInDB(): Boolean {
        return BaseApplication.baseDBHelper.queryIsExistKeyStore()
    }

    /**
     * 解析来自数据库的keystore文件
     *
     * @param keystore
     */
    fun parseKeystore(keystore: String): WalletBean? {
        var walletBean: WalletBean? = null
        try {
            val json =
                AESTool.decodeCBC_128(keystore, PreferenceTool.getInstance().getString(Constants.Preference.PASSWORD)!!)
            if (StringTool.isEmpty(json)) {
                LogTool.d(TAG, MessageConstants.KEYSTORE_IS_NULL)
            } else {
                walletBean = GsonTool.convert(json, WalletBean::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        LogTool.d(TAG, MessageConstants.WALLET_INFO + walletBean!!)
        return walletBean
    }
}