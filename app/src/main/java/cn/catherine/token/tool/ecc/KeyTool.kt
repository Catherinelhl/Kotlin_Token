package cn.catherine.token.tool.ecc

import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.encryption.Base58Tool
import org.bitcoinj.core.DumpedPrivateKey
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Sha256Hash
import org.bitcoinj.core.Utils
import org.bitcoinj.crypto.KeyCrypterException
import org.bitcoinj.params.MainNetParams
import org.spongycastle.asn1.sec.SECNamedCurves
import org.spongycastle.crypto.params.ECDomainParameters
import org.spongycastle.crypto.params.ECPrivateKeyParameters
import org.spongycastle.crypto.signers.ECDSASigner
import org.spongycastle.util.encoders.Base64
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 17:16
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.ecc
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class KeyTool {

    fun main(args: Array<String>) {

        val walletBean = WalletTool().createWallet()

        // 比特幣錢包地址
        val bitcoinAddressStr = walletBean!!.address
        // 比特幣私鑰WIF格式
        val bitcoinPrivateKeyWIFStr = walletBean.privateKey
        // String bitcoinPrivateKeyWIFStr =
        // "5KEKVAm9JbNjd9iVRz6xonNhGafrKmzQLRwGx5G33gXLeUHCfWm";
        // 比特幣公鑰((130 characters [0-9A-F]))
        val bitcoinPublicKeyStr = walletBean.publicKey
        // String bitcoinPublicKeyStr ="046604f1c0ce8029352e4bc2515c07c254ad4ad6116d44cda22d805f7f7d4dd5cdab812c6f9ec1d3d38c6f740e9af609125a416c9c17838d564650ad168c28bd1d";

        println("bitcoinPrivateKeyWIFStr = $bitcoinPrivateKeyWIFStr")
        println("bitcoinPublicKeyStr = $bitcoinPublicKeyStr")
        println("bitcoinAddressStr = $bitcoinAddressStr")

        //根據私鑰產生Wallet
        val usePrivateKeyWIFStrCreateWallet = WalletTool().createWallet(bitcoinPrivateKeyWIFStr)
        println("[usePrivateKeyWIFStrCreateWallet] bitcoinPrivateKeyWIFStr = " + usePrivateKeyWIFStrCreateWallet!!.privateKey)
        println("[usePrivateKeyWIFStrCreateWallet] bitcoinPublicKeyStr = " + usePrivateKeyWIFStrCreateWallet.publicKey)
        println("[usePrivateKeyWIFStrCreateWallet] bitcoinAddressStr = " + usePrivateKeyWIFStrCreateWallet.address)

        // 私鑰加簽
        val tcMessage =
            "{\"previous\":\"bb8ee265133794ba6a74e705fb8839539d41beb22ea7e075529b7b6dcee7506a\",\"blockService\":\"BCC\",\"blockType\":\"Open\",\"blockTxType\":\"Matrix\",\"sourceTxhash\":\"0f4e38eb39ac15befc5455782774a155f5f8a3fa36e49e7bb75968105ae43c70\",\"amount\":\"100\",\"representative\":\"1EykGQ6mNsoVNsdy9hM9frPNTWPg8jRCWG\",\"wallet\":\"1EykGQ6mNsoVNsdy9hM9frPNTWPg8jRCWG\",\"work\":\"0\",\"date\":\"1534940225033\"}"
        val signatureMessage = sign("5KhVkSGqV6jAyRbGuyg2DSDULZwGv9ueFdUzySxXxUZRzskBwmi", tcMessage)
        println("Signature Message = $signatureMessage")

        // 公鑰解簽
        val verifyResult2 = verify(
            "04c5f3dfd89ebb741e06e1f098f717ca21ee92e841f9754d177c847cefe23c5c2c593456968a223ff0b867ea8af0cb9ee0d4f238d4ec331a167b26d78fd9689bf6",
            signatureMessage,
            tcMessage
        )
        println("---------------------Verify RESULT Java = $verifyResult2")
        val verifyResult = verify(
            "04c5f3dfd89ebb741e06e1f098f717ca21ee92e841f9754d177c847cefe23c5c2c593456968a223ff0b867ea8af0cb9ee0d4f238d4ec331a167b26d78fd9689bf6",
            "Gy9FaEPdrUKraV3K5iN+EezQ2ad8MYOgkoLgYsuljfngAhUWKz8sRaCid3ixnOahRuei6QSgf4E9wsuPomDU8DU=",
            tcMessage
        )
        println("---------------------Verify RESULT iOS = $verifyResult")

        // 驗證地址是否符合比特幣規範
        val validateBitcoinAddress = validateBitcoinAddress(bitcoinAddressStr)
        println("validateBitcoinAddress = $validateBitcoinAddress")

        // 驗證公鑰
        val validateBitcoinPublicKeyStr = validateBitcoinPublicKeyStr(bitcoinPublicKeyStr)
        println("validateBitcoinPublicKeyStr = $validateBitcoinPublicKeyStr")
        // 驗證私鑰

        val validateBitcoinPrivateKeyWIFStr = validateBitcoinPrivateKeyWIFStr(bitcoinPrivateKeyWIFStr)
        println("validateBitcoinPrivateKeyWIFStr = $validateBitcoinPrivateKeyWIFStr")

    }

    // ========================================================================================================================
    /**
     *
     * Verification Signature
     *
     * @param publicKeyStr
     * @param signatureBase64Str
     * @param tcMessage
     *
     * @return boolean
     * @throws Exception
     */
    @Throws(Exception::class)
    fun verify(publicKeyStr: String, signatureBase64Str: String, tcMessage: String): Boolean {
        var tcMessage = tcMessage

        var verifySueecss = false

        // 使用secp256k1產生公鑰
        val params = SECNamedCurves.getByName("secp256k1")
        val CURVE = ECDomainParameters(
            params.curve, params.g, params.n,
            params.h
        )

        // 公鑰X軸
        val axisX = BigInteger(publicKeyStr.substring(2, 66), 16)
        // 公鑰Y軸
        val axisY = BigInteger(publicKeyStr.substring(66, publicKeyStr.length), 16)
        val ecPoint = CURVE.curve.createPoint(axisX, axisY)
        // 依照ECPoint轉為公鑰ECKey
        val fromPublicOnly = ECKey.fromPublicOnly(ecPoint)

        try {

            tcMessage = Sha256Tool().doubleSha256ToString(tcMessage)

            // 公鑰解簽
            fromPublicOnly.verifyMessage(tcMessage, signatureBase64Str)
            verifySueecss = true
        } catch (e: Exception) {
            LogTool.d("Verify Exception = " + e.message)
        }

        return verifySueecss

    }

    // ========================================================================================================================
    /**
     * Create Signature
     *
     * @param privateKeyStr
     * @param tcMessage
     * @return signatureMessage
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun sign(privateKeyWIFStr: String, tcMessage: String): String {
        var tcMessage = tcMessage

        // 私鑰WIF字串轉ECKey
        val privateKey = DumpedPrivateKey.fromBase58(MainNetParams.get(), privateKeyWIFStr).key

        tcMessage = Sha256Tool().doubleSha256ToString(tcMessage)

        return signMessage(privateKey, tcMessage)

    }

    // ========================================================================================================================
    /**
     * Checks if the given String is a valid Bitcoin address.
     *
     * @param address
     * The address to check
     * @return True, if the String is a valid Bitcoin address, false otherwise
     */
    fun validateBitcoinAddress(address: String?): Boolean {

        if (address == null) {
            return false
        }

        // Check the length
        if (address.length < 26 || address.length > 35) {
            return false
        }

        val addressBytes = Base58Tool.decode(address)

        // Check the version byte
        if (addressBytes[0].toInt() != 0) {
            return false
        }

        val md: MessageDigest
        try {
            md = MessageDigest.getInstance("SHA-256")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("BitcoinUtils.validateBitcoinAddress: SHA-256 digest not found")
        }

        md.update(addressBytes, 0, 21)
        var sha256Hash = md.digest()
        sha256Hash = md.digest(sha256Hash)

        val addressChecksum = Arrays.copyOfRange(addressBytes, 21, addressBytes.size)
        val calculatedChecksum = Arrays.copyOfRange(sha256Hash, 0, 4)
        return Arrays.equals(addressChecksum, calculatedChecksum)
    }

    // ========================================================================================================================
    /**
     * Checks if the given String is a valid PublicKey String.
     *
     * @param publicKeyStr
     * The PublicKey String to check
     * @return True, if the String is a valid Bitcoin PublicKey String, false
     * otherwise
     */
    fun validateBitcoinPublicKeyStr(publicKeyStr: String): Boolean {

        return publicKeyStr.matches("^04[a-f0-9]{128}".toRegex())

    }
    // ========================================================================================================================
    /**
     * Checks if the given String is a valid PublicKey String.
     *
     * @param publicKeyStr
     * The PublicKey String to check
     * @return True, if the String is a valid Bitcoin PublicKey String, false
     * otherwise
     */
    fun validateBitcoinPrivateKeyWIFStr(privateKeyStr: String): Boolean {

        return privateKeyStr.matches("^5[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{50}".toRegex())

    }

    /**
     * Signs a text message using the standard Bitcoin messaging signing format and
     * returns the signature as a base64 encoded string.
     *
     * @throws IllegalStateException
     * if this ECKey does not have the private part.
     * @throws KeyCrypterException
     * if this ECKey is encrypted and no AESKey is provided or it does
     * not decrypt the ECKey.
     */
    @Throws(KeyCrypterException::class)
    fun signMessage(ecKey: ECKey, message: String): String {

        val data = Utils.formatMessageForSigning(message)
        // Twice Sha256
        val hash = Sha256Hash.twiceOf(data)

        // 使用預設RandomDSAKCalculator
        val signer = ECDSASigner()

        val privKey = ECPrivateKeyParameters(ecKey.privKey, ECKey.CURVE)
        signer.init(true, privKey)
        val components = signer.generateSignature(hash.bytes)
        val sig = ECKey.ECDSASignature(components[0], components[1]).toCanonicalised()

        val ecKeyPublicAsStr = ecKey.publicKeyAsHex
        // Now we have to work backwards to figure out the recId needed to recover the
        var recId = -1
        for (i in 0..3) {
            val key = ECKey.recoverFromSignature(i, sig, hash, ecKey.isCompressed)
            val recoverFromSignatureStr = key!!.publicKeyAsHex
            // Check Public Key String
            if (key != null && ecKeyPublicAsStr == recoverFromSignatureStr) {
                recId = i
                break
            }
        }

        if (recId == -1) {
            throw RuntimeException("Could not construct a recoverable key. This should never happen.")
        }

        val headerByte = recId + 27 + if (ecKey.isCompressed) 4 else 0
        // 1 header + 32 bytes for R + 32 bytes for S
        val sigData = ByteArray(65)
        sigData[0] = headerByte.toByte()
        System.arraycopy(Utils.bigIntegerToBytes(sig.r, 32), 0, sigData, 1, 32)
        System.arraycopy(Utils.bigIntegerToBytes(sig.s, 32), 0, sigData, 33, 32)
        return String(Base64.encode(sigData), Charset.forName("UTF-8"))
    }
}