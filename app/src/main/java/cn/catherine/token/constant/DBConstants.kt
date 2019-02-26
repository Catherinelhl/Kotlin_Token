package cn.catherine.token.constant

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 14:33
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.constant
+--------------+---------------------------------
+ description  +   定義數據庫字段常數
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object DBConstants {

    const val DB_NAME = "Bcaas"// 当前数据库的名字
    const val BCAAS_SECRET_KEY = "BcaasSecretKey"//存储当前钱包信息的表
    const val BCAAS_ADDRESS = "BcaasAddress"//存储地址管理的表
    const val UID = "uid"
    const val KEYSTORE = "keyStore"
    const val CREATE_TIME = "createTime"
    const val ADDRESS_NAME = "addressName"
    const val ADDRESS = "address"
}