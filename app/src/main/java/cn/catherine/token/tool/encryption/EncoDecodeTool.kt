package cn.catherine.token.tool.encryption

import cn.catherine.token.constant.MessageConstants

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 18:10
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

class EncoDecodeTool {
    enum class EncodeType {
        EncodeType_3DES_ECB, EncodeType_3DES_CBC,

        EncodeType_AES_128_ECB, EncodeType_AES_128_CBC

    }

    fun encode(type: EncodeType, content: String): String {

        var result = MessageConstants.Empty
        when (type) {

            EncoDecodeTool.EncodeType.EncodeType_3DES_CBC -> try {
                result = Des3Tool.encodeCBC(content)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            EncoDecodeTool.EncodeType.EncodeType_3DES_ECB -> try {
                result = Des3Tool.encodeECB(content)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            EncoDecodeTool.EncodeType.EncodeType_AES_128_CBC -> try {
                result = AESTool.encodeCBC_128(content)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            EncoDecodeTool.EncodeType.EncodeType_AES_128_ECB -> try {
                result = AESTool.encodeECB_128(content)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            else -> {
            }
        }

        return result
    }

    fun decode(type: EncodeType, hash: String): String {

        var result = ""

        when (type) {
            EncoDecodeTool.EncodeType.EncodeType_3DES_CBC -> try {
                result = Des3Tool.decodeCBC(hash)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            EncoDecodeTool.EncodeType.EncodeType_3DES_ECB -> try {
                result = Des3Tool.decodeECB(hash)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            EncoDecodeTool.EncodeType.EncodeType_AES_128_CBC -> try {
                result = AESTool.decodeCBC_128(hash)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            EncoDecodeTool.EncodeType.EncodeType_AES_128_ECB -> try {
                result = AESTool.decodeECB_128(hash)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            else -> {
            }
        }
        return result
    }
}