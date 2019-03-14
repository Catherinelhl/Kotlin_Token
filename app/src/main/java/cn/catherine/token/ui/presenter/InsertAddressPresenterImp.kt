package cn.catherine.token.ui.presenter

import cn.catherine.token.R
import cn.catherine.token.base.BaseApplication
import cn.catherine.token.db.vo.AddressVO
import cn.catherine.token.ui.constract.InsertAddressContract

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午5:04
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.presenter
+--------------+---------------------------------
+ description  +   Presenter：「添加地址」界面需要的數據獲取&處理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class InsertAddressPresenterImp(private val view:InsertAddressContract.View) : InsertAddressContract.Presenter{
    override fun saveData(addressVO: AddressVO) {
        //向数据库里面插入新添加的地址信息
        if (addressVO == null) {
            return
        }
        if (BaseApplication.baseDBHelper != null) {
            view.hideLoading()
            val status = BaseApplication.baseDBHelper.queryIsExistAddress(addressVO)
            when (status) {
                -1//沒有重複的
                -> {
                    val result = BaseApplication.baseDBHelper.insertAddress(addressVO)
                    if (result == 0L) {
                        view.saveDataFailure()
                    } else {
                        view.saveDataSuccess()

                    }
                }
                0//數據異常
                -> view.saveDataFailure()
                1//命名重複
                -> view.addressRepeat(BaseApplication.context.resources.getString(R.string.address_name_repeat))
                2//地址重複
                -> view.addressRepeat(BaseApplication.context.resources.getString(R.string.address_repeat))
            }

        } else {
            view.saveDataFailure()
        }
    }
}