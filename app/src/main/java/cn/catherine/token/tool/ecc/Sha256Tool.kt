package cn.catherine.token.tool.ecc

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 17:17
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

class Sha256Tool {
    /**
     * Double Sha256 String
     *
     * @param message
     * @return double Sha256 String
     * @throws NoSuchAlgorithmException
     */
    @Throws(NoSuchAlgorithmException::class)
    fun doubleSha256ToString(message: String): String {

        val digest = MessageDigest.getInstance("SHA-256")
        val shaFirst = digest.digest(message.toByteArray())
        val shaSecond = digest.digest(shaFirst)
        return bytesToHex(shaSecond)

    }

    // ========================================================================================================================
    /**
     * Sha-256 String
     *
     * @param message
     * @return double Sha256 String
     * @throws NoSuchAlgorithmException
     */
    @Throws(NoSuchAlgorithmException::class)
    fun sha256ToString(message: String): String {

        val digest = MessageDigest.getInstance("SHA-256")
        val encodedhash = digest.digest(message.toByteArray(StandardCharsets.UTF_8))
        return bytesToHex(encodedhash)
    }

    // ========================================================================================================================
    /**
     * Sha-256 To byte[]
     *
     * @param message
     * @return byte[]
     * @throws NoSuchAlgorithmException
     */
    @Throws(NoSuchAlgorithmException::class)
    fun sha256ToByte(message: String): ByteArray {

        val digest = MessageDigest.getInstance("SHA-256")

        return digest.digest(message.toByteArray(StandardCharsets.UTF_8))
    }

    /**
     * Bytes To Hex
     *
     * @param hash
     * @return Hex String
     */
    private fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuffer()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1)
                hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }
}