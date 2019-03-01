package cn.catherine.token.tool.ecc

import cn.catherine.token.bean.WalletBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.*
import cn.catherine.token.tool.wallet.WalletDBTool
import cn.catherine.token.vo.PublicUnitVO
import org.bitcoinj.core.DumpedPrivateKey
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Utils.HEX
import org.bitcoinj.params.MainNetParams
import java.math.BigInteger
import java.util.ArrayList

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 17:18
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.ecc
+--------------+---------------------------------
+ description  +   创建钱包的配置
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class WalletTool {
    private val TAG = WalletTool::class.java.simpleName

    fun createWallet(): WalletBean? {

        try {

            // 比特幣主網參數
            val mainNetParams = MainNetParams.get()
            // 取得私鑰WIF格式
            val privateKeyAsHex = ECKey().privateKeyAsHex
            val privateKeyInt = BigInteger(1, HEX.decode(privateKeyAsHex.toLowerCase()))
            // 未壓縮
            val privateKey = ECKey.fromPrivate(privateKeyInt, false)

            val walletBean = WalletBean()
            walletBean.privateKey = privateKey.getPrivateKeyAsWiF(mainNetParams)
            // 公鑰(長度130)
            walletBean.publicKey = privateKey.getPublicKeyAsHex()
            // 產生地址
            walletBean.address = privateKey.toAddress(mainNetParams).toBase58()

            return walletBean

        } catch (e: Exception) {
            LogTool.e(TAG, e.toString())
            e.printStackTrace()

        }

        return null

    }

    fun createWallet(privateKeyAsWiFStr: String): WalletBean? {

        try {

            if (KeyTool().validateBitcoinPrivateKeyWIFStr(privateKeyAsWiFStr)) {
                // 比特幣主網參數
                val mainNetParams = MainNetParams.get()
                // 私鑰WIF格式字串取得ECKey
                val privateKey = DumpedPrivateKey.fromBase58(mainNetParams, privateKeyAsWiFStr).key

                val wallet = WalletBean()
                wallet.privateKey = privateKey.getPrivateKeyAsWiF(mainNetParams)
                // 公鑰(長度130)
                wallet.publicKey = privateKey.publicKeyAsHex
                // 產生地址
                wallet.address = privateKey.toAddress(mainNetParams).toBase58()

                return wallet
            }

        } catch (e: Exception) {
            LogTool.d<Any>(TAG, MessageConstants.WALLET_CREATE_EXCEPTION + e.message)
        }

        return null


    }

    /* 自动创建钱包信息*/
    fun getWalletInfo(): WalletBean? {
        return getWalletInfo("")
    }

    /*通过WIF格式的私钥来创建钱包*/
    fun getWalletInfo(privateKeyWIFStr: String): WalletBean? {
        return if (StringTool.isEmpty(privateKeyWIFStr)) {
            WalletTool().createWallet()
        } else {
            WalletTool().createWallet(privateKeyWIFStr)

        }

    }

    //通过默认的方式来获取钱包地址
    fun getWalletAddress(): String {
        return getWalletInfo("")!!.address
    }

    //通过WIF格式的私钥来获取钱包地址信息
    fun getWalletAddress(privateKeyWIFStr: String): String {
        return getWalletInfo(privateKeyWIFStr)!!.address
    }


    /**
     * 获取blockService
     *
     * @return
     */
    fun getPublicUnitVO(): List<PublicUnitVO>? {
        var publicUnitVOS = GlobalVariableManager.publicUnitVOList
        //如果当前获取的数据列表为空，那么设置默认的币种信息
        if (ListTool.isEmpty<PublicUnitVO>(publicUnitVOS)) {
            publicUnitVOS = ArrayList<PublicUnitVO>()
            //设置默认的BlockService
            val publicUnitVO = PublicUnitVO()
            publicUnitVO.blockService = Constants.BlockService.BCC
            publicUnitVO.isStartup = Constants.BlockService.OPEN
            publicUnitVOS.add(publicUnitVO)
        }
        return publicUnitVOS
    }

    /**
     * 得到当前需要显示的币种
     *
     * @return
     */
    fun getDisplayBlockService(): String? {
        val publicUnitVOS = getPublicUnitVO()
        //1:设置默认币种
        val blockService = GlobalVariableManager.blockService
        if (ListTool.noEmpty(publicUnitVOS)) {
            //2:比对默认BCC的币种是否关闭，否则重新赋值
            var isStartUp = Constants.BlockService.CLOSE
            for (publicUnitVO in publicUnitVOS!!) {
                if (StringTool.equals(blockService, publicUnitVO.blockService)) {
                    isStartUp = publicUnitVO.isStartup
                    break
                }
            }
            return if (StringTool.equals(isStartUp, Constants.BlockService.OPEN)) {
                blockService
            } else {
                publicUnitVOS[0].blockService

            }
        } else {
            return blockService
        }
    }

    /**
     * 解析当前私钥，得到新的钱包地址信息
     *
     * @param WIFPrivateKey
     * @return 如果返回false，代表不通过，需要用户重新输入
     */
    fun parseWIFPrivateKey(WIFPrivateKey: String): Boolean {
        //检验导入私钥格式
        if (!KeyTool().validateBitcoinPrivateKeyWIFStr(WIFPrivateKey)) {
            return false
        }
        val walletBean = WalletTool().getWalletInfo(WIFPrivateKey) ?: return false
        GlobalVariableManager.blockService = Constants.BlockService.BCC
        PreferenceTool.getInstance().saveString(Constants.Preference.PUBLIC_KEY, walletBean!!.getPublicKey())
        PreferenceTool.getInstance().saveString(Constants.Preference.PRIVATE_KEY, walletBean!!.getPrivateKey())
        GlobalVariableManager.walletBean = walletBean//将当前的账户地址赋给Application，这样就不用每次都去操作数据库
        LogTool.d<Any>(TAG, walletBean)
        return true
    }

    /**
     * 保存当前的钱包信息
     *
     * @param password
     */
    fun createAndSaveWallet(password: String): WalletBean {
        //1:创建钱包
        val walletBean = WalletTool().getWalletInfo()
        //2:并且保存钱包的公钥，私钥，地址，密码
        val walletAddress = walletBean!!.address
        GlobalVariableManager.blockService = Constants.BlockService.BCC
        PreferenceTool.getInstance().saveString(Constants.Preference.PASSWORD, password)
        PreferenceTool.getInstance().saveString(Constants.Preference.PUBLIC_KEY, walletBean!!.getPublicKey())
        PreferenceTool.getInstance().saveString(Constants.Preference.PRIVATE_KEY, walletBean!!.getPrivateKey())
        GlobalVariableManager.walletBean = walletBean//将当前的账户地址赋给Application，这样就不用每次都去操作数据库
        WalletDBTool().insertWalletInDB(walletBean)
        return walletBean
    }

    fun getTVDefaultPrivateKey(): String {
        return when {
            DeviceTool().getDeviceModel() == "A4021" -> //如果是skyWorth
                "5KEJMiY5LskP3S54hcuVKD9zJmb24EYNSi6vGTnEPvve7vMzGCq"//16ugnJ7pndAFJJfMwoSDFbNTwzHvxhL1cL
            DeviceTool().getDeviceModel() == "MiBOX4" -> //如果是Mixbox
                "5JgdiMaqPPkMKYUn2yBZpgwGEv148552ehJVwNfyXrVCv2TE1AS"//14s8keV4ZR4JWNiQ1kXQPdwtKkCtftEsrj
            else -> "5K1E46ewNMyd2ZRYsKQZLXJz8G4k8B9e2jeVTtLmy8FZUKdv3uG"//1Aej3CTNxdrhxdmRUTTmthdaKphurT1Aau
        }
    }
}