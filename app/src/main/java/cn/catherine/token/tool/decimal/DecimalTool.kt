package cn.catherine.token.tool.decimal

import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.StringTool
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 16:28
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool.decimal
+--------------+---------------------------------
+ description  +   工具類：DecimalTool
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class DecimalTool {
    private val TAG = DecimalTool::class.java.simpleName

    /**
     * 第一個參數 - 第二個參數 ＝ 回傳的數值
     * <br></br>
     * FirstValue不能小於SecondValue
     *
     * @param firstValue
     * @param secondValue
     */
    @Throws(Exception::class)
    fun calculateFirstSubtractSecondValue(firstValue: String, secondValue: String): String {

        val decimalFormat = DecimalFormat("0.00000000")

        // 計算小數八位，第九位無條件捨去
        val bigDecimalFirstValue = BigDecimal(firstValue).setScale(8, RoundingMode.FLOOR)
        val bigDecimalSecondValue = BigDecimal(secondValue).setScale(8, RoundingMode.FLOOR)

        // FirstValue不能小於SecondValue
        if (bigDecimalFirstValue.compareTo(bigDecimalSecondValue) == -1) {
            return MessageConstants.NO_ENOUGH_BALANCE
        }

        val bigDecimalNum = bigDecimalFirstValue.subtract(bigDecimalSecondValue)

        return decimalFormat.format(bigDecimalNum)
    }

    /**
     * 轉換成顯示每三位數加逗號，小數只顯示八位
     *
     * @param decimal
     */
    fun transferDisplay(decimal: String?): String {
        var decimal = decimal
        if (StringTool.isEmpty(decimal)) {
            decimal = "0"
        }

        val decimalFormat = DecimalFormat("#,##0.00000000")

        val bigDecimal = BigDecimal(decimal).setScale(8, RoundingMode.FLOOR)

        return decimalFormat.format(bigDecimal)
    }

    /**
     * 轉換成小數只顯示八位
     *
     * @param decimal
     */
    fun transferStoreDatabase(decimal: String): String {

        val decimalFormat = DecimalFormat("0.00000000")

        val bigDecimal = BigDecimal(decimal).setScale(8, RoundingMode.FLOOR)

        return decimalFormat.format(bigDecimal)
    }

    /**
     * 第一個參數 + 第二個參數 ＝ 回傳的數值 <br></br>
     *
     * @param firstValue
     * @param secondValue
     */
    fun calculateFirstAddSecondValue(firstValue: String, secondValue: String): String {
        if (StringTool.isEmpty(firstValue)) {
            return secondValue
        }
        if (StringTool.isEmpty(secondValue)) {
            return firstValue
        }
        val decimalFormat = DecimalFormat("0.00000000")

        // 計算小數八位，第九位無條件捨去
        val bigDecimalFirstValue = BigDecimal(firstValue).setScale(8, RoundingMode.FLOOR)
        val bigDecimalSecondValue = BigDecimal(secondValue).setScale(8, RoundingMode.FLOOR)

        // 任一value不能小於0
        if (bigDecimalFirstValue.compareTo(BigDecimal.ZERO) == -1 || bigDecimalSecondValue.compareTo(BigDecimal.ZERO) == -1) {
            return MessageConstants.AMOUNT_EXCEPTION_CODE
        }

        val bigDecimalNum = bigDecimalFirstValue.add(bigDecimalSecondValue)

        return decimalFormat.format(bigDecimalNum)
    }

    /**
     * 比較第一個值是否等於第二個值
     *
     * @param firstValue
     * @param secondValue
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun compareFirstEqualSecondValue(firstValue: String, secondValue: String): Boolean {
        // 計算小數八位，第九位無條件捨去
        val bigDecimalFirstValue = BigDecimal(firstValue).setScale(8, RoundingMode.FLOOR)
        val bigDecimalSecondValue = BigDecimal(secondValue).setScale(8, RoundingMode.FLOOR)

        return if (bigDecimalFirstValue.compareTo(bigDecimalSecondValue) == 0) {
            true
        } else false
    }
}