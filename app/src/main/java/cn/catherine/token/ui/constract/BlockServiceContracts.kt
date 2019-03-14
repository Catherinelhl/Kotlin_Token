package cn.catherine.token.ui.constract

import cn.catherine.token.base.BaseContract
import cn.catherine.token.vo.PublicUnitVO

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午4:47
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.constract
+--------------+---------------------------------
+ description  +  連接界面和數據操作互動：「獲取幣種」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface BlockServiceContracts {
    interface View : BaseContract.View {
        //獲取清單文件成功
        fun getBlockServicesListSuccess(from: String, publicUnitVOList: List<PublicUnitVO>)

        //獲取清單文件失败
        fun getBlockServicesListFailure(from: String)

        // 沒有可顯示的幣種
        fun noBlockServicesList(from: String)
    }

    interface Presenter {
        //獲取幣種清單
        fun getBlockServiceList(from: String)
    }
}