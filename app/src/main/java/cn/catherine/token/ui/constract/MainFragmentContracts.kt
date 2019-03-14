package cn.catherine.token.ui.constract

import cn.catherine.token.base.BaseContract

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午4:49
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.constract
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface MainFragmentContracts {
    interface View : BaseContract.View {
        //获取交易记录失败
        fun getAccountDoneTCFailure(message: String)

        //获取交易记录成功
        fun getAccountDoneTCSuccess(objectList: List<Any>)

        //没有交易记录
        fun noAccountDoneTC()

        //當前點擊更多
        fun getNextObjectId(nextObjectId: String)
    }

    interface Presenter : BaseContract.Presenter {
        //获取账户已完成交易
        fun getAccountDoneTC(nextObjectId: String)

    }
}