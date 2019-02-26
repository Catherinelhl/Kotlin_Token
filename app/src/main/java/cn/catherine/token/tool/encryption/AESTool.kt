package cn.catherine.token.tool.encryption

import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.regex.RegexTool
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 15:24
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.encryption
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object AESTool {

    // 密鑰
    private const val secretKey_128 = "jdcv@888@jdcv888"
    private const val secretKey_256 = "jdcv@888@jdcv888jdcv@888@jdcv888"
    // 向量
    private const val iv = "qwertyuioplkjhgg"

    // 加解密統一使用的編碼方式
    private const val encoding= "utf-8"

    // 密碼八位固定
    private const val secretKey_128_fixed = "jdcv@888"

    // 密碼8~16位
    private const val secretKey_128_1 = "a"
    private const val secretKey_128_2 = "b2"
    private const val secretKey_128_3 = "cd3"
    private const val secretKey_128_4 = "e@f4"
    private const val secretKey_128_5 = "ghij5"
    private const val secretKey_128_6 = "k#lmn6"
    private const val secretKey_128_7 = "opqrst7"
    private const val secretKey_128_8 = "uv@wxyz8"

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encodeCBC_128(plainText: String): String {
        val deskey: Key? = null
        val raw = secretKey_128.toByteArray()
        val sKeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")// "算法/模式/補碼方式"
        Cipher.getInstance("AES/CBC/PKCS5Padding")

        val ips = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ips)
        val encryptData = cipher.doFinal(plainText.toByteArray(charset(encoding)))
        return Base64Tool.encode(encryptData)
    }

    /**
     * 3DES加密
     *
     * @param plainText    普通文本
     * @param secretKey128 加密金鑰
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encodeCBC_128(plainText: String, secretKey128: String): String {
        var secretKey128 = secretKey128
        if (!RegexTool.isValidatePassword(secretKey128)) { // 正則表達式驗證密碼
            return MessageConstants.Empty
        }
        secretKey128 = setSecretKey(secretKey128)
        val raw = secretKey128.toByteArray()
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")//"算法/模式/補碼方式"
        Cipher.getInstance("AES/CBC/PKCS5Padding")

        val ips = IvParameterSpec(secretKey128.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips)
        val encryptData = cipher.doFinal(plainText.toByteArray(charset(encoding)))
        return Base64Tool.encode(encryptData)
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decodeCBC_128(encryptText: String): String {
        val deskey: Key? = null
        val raw = secretKey_128.toByteArray()
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        Cipher.getInstance("AES/CBC/PKCS5Padding")

        val ips = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips)

        val decryptData = cipher.doFinal(Base64Tool.decode(encryptText))

        return String(decryptData, charset(encoding))
    }

    /**
     * 3DES解密
     *
     * @param encryptText  加密文本
     * @param secretKey128 解密金鑰
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decodeCBC_128(encryptText: String, secretKey128: String): String {
        var secretKey128 = secretKey128
        if (!RegexTool.isValidatePassword(secretKey128)) { // 正則表達式驗證密碼
            return MessageConstants.Empty
        }
        secretKey128 = setSecretKey(secretKey128)
        val raw = secretKey128.toByteArray()
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        Cipher.getInstance("AES/CBC/PKCS5Padding")

        val ips = IvParameterSpec(secretKey128.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips)

        val decryptData = cipher.doFinal(Base64Tool.decode(encryptText))

        return String(decryptData, charset(encoding))
    }

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encodeECB_128(plainText: String): String {
        val deskey: Key? = null
        val raw = secretKey_128.toByteArray()
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        Cipher.getInstance("AES/ECB/PKCS5Padding")

        val ips = IvParameterSpec("".toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips)
        val encryptData = cipher.doFinal(plainText.toByteArray(charset(encoding)))
        return Base64Tool.encode(encryptData)
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decodeECB_128(encryptText: String): String {
        val deskey: Key? = null
        val raw = secretKey_128.toByteArray()
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        Cipher.getInstance("AES/ECB/PKCS5Padding")

        val ips = IvParameterSpec("".toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips)

        val decryptData = cipher.doFinal(Base64Tool.decode(encryptText))

        return String(decryptData, charset(encoding))
    }

    // 使用者輸入密碼8~16位, 密碼不足16位, 後綴補上設定好的密碼
    private fun setSecretKey(secretKey128: String): String {
        var secretKey128 = secretKey128
        when (secretKey128.length) {
            8 -> secretKey128 += secretKey_128_8
            9 -> secretKey128 += secretKey_128_7
            10 -> secretKey128 += secretKey_128_6
            11 -> secretKey128 += secretKey_128_5
            12 -> secretKey128 += secretKey_128_4
            13 -> secretKey128 += secretKey_128_3
            14 -> secretKey128 += secretKey_128_2
            15 -> secretKey128 += secretKey_128_1
            16 -> {
            }
        }// 16位密碼不需補
        return secretKey128
    }
}