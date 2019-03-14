package cn.catherine.token.ui.constract

import cn.catherine.token.base.BaseContract
import cn.catherine.token.db.vo.AddressVO

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午4:48
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.constract
+--------------+---------------------------------
+ description  +   連接界面和數據操作互動：「新增地址」
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface InsertAddressContract {

    interface View : BaseContract.View {
        fun saveDataSuccess()
        fun saveDataFailure()
        fun addressRepeat(info: String)
    }

    interface Presenter {
        fun saveData(addressVO: AddressVO)
    }
}