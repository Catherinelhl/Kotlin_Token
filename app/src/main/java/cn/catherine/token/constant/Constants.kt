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

    }

    object ValueMaps {
        const val FROM_BRAND: String = "fromBrand"

    }


    object Time {
        const val sleep500: Int = 500
        const val sleep400: Int = 400
        const val sleep200: Int = 200
        const val sleep100: Int = 100
        const val sleep1000: Int = 1000
        const val sleep10000: Int = 10000
        const val sleep800: Int = 800
        const val sleep2000: Int = 2000
        const val INTERNET_TIME_OUT: Long = 5 * 60 * 1000//内网连接时间，ms，超时5s之后
        const val EXTERNAL_TIME_OUT: Long = 10 * 60 * 1000//外网连接超时时间，超过10s之后
        const val STAY_AUTH_ACTIVITY: Int = 3//如果当前不用编辑页面，停留在页面的时间3s
        const val STAY_BRAND_ACTIVITY: Long = 2//如果当前不用编辑页面，停留在页面的时间2s

        const val LONG_TIME_OUT: Int = 30//设置超时时间
        const val COUNT_DOWN_TIME: Long = 10//倒数计时
        const val COUNT_DOWN_RECEIVE_BLOCK: Long = 1 //接收「receive」TCP响应结果倒计时
        const val COUNT_DOWN_NOTIFICATION: Long = 2// 通知倒计时
        const val COUNT_DOWN_REPRESENTATIVES: Long = 1//停留当前在「change」授权页面的时间
        const val COUNT_DOWN_GUIDE_TV: Long = 200//TV版Guide頁面需要的倒計時
        const val GET_RECEIVE_BLOCK: Long = 10//获取未签章区块间隔=
        const val GET_BALANCE: Long = 10//获取餘額
        const val HEART_BEAT: Long = 30//TCP  C-S 发送心跳信息间隔
        const val PRINT_LOG: Long = 1 //打印当前设备的内存
        const val TOAST_LONG: Int = 3
        const val TOAST_SHORT: Int = 0
    }

}