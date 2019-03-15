package cn.catherine.token.constant

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 14:31
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.constant
+--------------+---------------------------------
+ description  +   定義网路请求對應Config常數(API)
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object HostURLConstants {

    /*存储当前连接服务器的类型 国际PRD*/
     var serverType = Constants.ServerType.INTERNATIONAL_PRD

    /****************HTTP [SFN] API [START]  */
    /*Internet*/
    const val SFN_URL_INTERNATIONAL_SIT_SGPAWS = "http://sitsfnsgpaws.bcaas.io:20000"
    const val SFN_URL_INTERNATIONAL_SIT_JPGOOGLE = "http://sitsfnjpgoogle.bcaas.io:20000"

    //UAT
    const val SFN_URL_INTERNATIONAL_UAT = "http://uatsn.bcaas.io:20000"
    const val SFN_URL_INTERNATIONAL_UAT_SN_ALI = "http://uatsnali.bcaas.io:20000"
    const val SFN_URL_INTERNATIONAL_UAT_SN_GOOGLE = "http://uatsngoogle.bcaas.io:20000"

    //PRD
    const val SFN_URL_INTERNATIONAL_PRD_AWSJP = "http://sfnsgpaws1.bcaas.io:20000"
    const val SFN_URL_INTERNATIONAL_PRD_ALIJP = "http://sfnsgpaws2.bcaas.io:20000"
    const val SFN_URL_INTERNATIONAL_PRD_GOOGLEJP = "http://sfnjpgoogle1.bcaas.io:20000"
    const val SFN_URL_INTERNATIONAL_PRD_GOOGLESGP = "http://sfnjpgoogle2:20000"

    /****************HTTP [SFN] API [END] ****************/


    /***************BcassApplication api,默認端口80 [START]  */

    /*Internet*/
    //SIT
    const val APPLICATION_URL_INTERNATIONAL_SIT = "https://sitapplication.bcaas.io"
    //UAT
    const val APPLICATION_URL_INTERNATIONAL_UAT = "https://uatapp.bcaas.io/"
    //PRO
    const val APPLICATION_URL_INTERNATIONAL_PRO = "https://application.bcaas.io"


    /***************BcassApplication api,默認端口80 [END] ********************/


    /********************Update Server ,默认端口80 [START]  */

    /*Internet*/
    //SIT
    const val UPDATE_URL_INTERNATIONAL_SIT = "https://situpdate.bcaas.io"
    //UAT
    const val UPDATE_URL_INTERNATIONAL_UAT = "https://uatup.bcaas.io"
    //PRO
    const val UPDATE_URL_INTERNATIONAL_PRO = "https://update.bcaas.io"

    /********************Update Server ,默认端口80 [END] ***********************/
}