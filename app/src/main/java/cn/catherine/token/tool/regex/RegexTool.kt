package cn.catherine.token.tool.regex

import java.util.regex.Pattern

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 15:03
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.regex
+--------------+---------------------------------
+ description  +   工具類：正則表達式
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object RegexTool {
    private const val EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private const val MONEY = "^\\d{n}$"
    const val PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9!@#$%^&*_]{8,16}$"
    const val REPLACE_BLANK = "\t|\r|\n|\\s*"
    const val IS_CHINESE = "[\u4e00-\u9fa5]+"
    private const val VERSION = "^-?[\\d.]+(?:e-?\\d+)?$"

    private const val AUTHNODE_AUTHORIZE_KEY = "OrAanUgeTBlHocNkBOcaDasE"
    private const val PC_AUTHORIZE_KEY = "OranPgeBlockBCcaas"
    private const val MAC_AUTHORIZE_KEY = "OraMngeBAlockBCcaas"
    private const val IOS_AUTHORIZE_KEY = "OrangeiBlockOBcaasS"
    private const val ANDROID_AUTHORIZE_KEY = "OrAanNgeDBlRocOkBOcaIasD"


    private fun getPattern(regex: String): Pattern {

        return Pattern.compile(regex)
    }

    fun isRightPassword(version: String): Boolean {

        val pattern = getPattern(PASSWORD)

        val matcher = pattern.matcher(version)

        return matcher.matches()
    }

    fun isRightMoney(version: String): Boolean {

        val pattern = getPattern(MONEY)

        val matcher = pattern.matcher(version)

        return matcher.matches()
    }

    fun isValidateVersion(version: String): Boolean {

        val pattern = getPattern(VERSION)

        val matcher = pattern.matcher(version)

        return matcher.matches()
    }

    fun isValidateAuthNodeKey(authKey: String): Boolean {
        return authKey == AUTHNODE_AUTHORIZE_KEY
    }

    fun isValidatePCKey(authKey: String): Boolean {
        return authKey == PC_AUTHORIZE_KEY
    }

    fun isValidateMacKey(authKey: String): Boolean {
        return authKey == MAC_AUTHORIZE_KEY
    }

    fun isValidateIOSKey(authKey: String): Boolean {
        return authKey == IOS_AUTHORIZE_KEY
    }

    fun isValidateAndroidKey(authKey: String): Boolean {
        return authKey == ANDROID_AUTHORIZE_KEY
    }

    fun isValidatePassword(password: String): Boolean {

        val pattern = getPattern(PASSWORD)

        val matcher = pattern.matcher(password)

        return matcher.matches()
    }

    /*判断是否是字符*/
    fun isCharacter(str: String): Boolean {
        return str.matches(PASSWORD.toRegex())
    }

    /*将当前的空格替换掉*/
    fun replaceBlank(src: String?): String {
        var dest = ""
        if (src != null) {
            val pattern = Pattern.compile(REPLACE_BLANK)
            val matcher = pattern.matcher(src)
            dest = matcher.replaceAll("")
        }
        return dest
    }

    //是否是中文
    fun isChinese(s: String): Boolean {
        return s.matches("[\u4e00-\u9fa5]+".toRegex())
    }
}