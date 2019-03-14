package cn.catherine.token.ui.constract

import cn.catherine.token.db.vo.AddressVO

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
+ description  +   連接界面和數據操作互動：「地址管理」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface AddressManagerContract {
    interface View {
        fun getAddresses(addressVOS: List<AddressVO>)

        fun noAddress()
    }

    interface Presenter {
        fun queryAllAddresses()

        fun deleteSingleAddress(addressVOBean: AddressVO)
    }
}