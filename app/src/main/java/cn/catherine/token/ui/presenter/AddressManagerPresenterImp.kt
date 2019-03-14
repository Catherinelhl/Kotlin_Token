package cn.catherine.token.ui.presenter

import cn.catherine.token.base.BaseApplication
import cn.catherine.token.db.vo.AddressVO
import cn.catherine.token.ui.constract.AddressManagerContract

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/11 下午4:50
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.presenter
+--------------+---------------------------------
+ description  +  Presenter：「地址管理」界面需要的數據獲取&處理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

open class AddressManagerPresenterImp (private val view:AddressManagerContract.View): AddressManagerContract.Presenter {
    override fun queryAllAddresses() {
        val addressVOBeans = getWalletsAddressesFromDB()
        if (addressVOBeans == null) {
            view.noAddress()
        } else {
            if (addressVOBeans.isEmpty()) {
                view.noAddress()
            } else {
                view.getAddresses(addressVOBeans)

            }
        }
    }

    override fun deleteSingleAddress(addressVOBean: AddressVO) {
        deleteAddressDataFromDB(addressVOBean)
        val addressVOList = getWalletsAddressesFromDB()
        if (addressVOList == null) {
            view.noAddress()
        } else {
            if (addressVOList.isEmpty()) {
                view.noAddress()
            } else {
                view.getAddresses(addressVOList)

            }
        }
    }


    //从数据库里面删除相对应的地址信息
    private fun deleteAddressDataFromDB(addressVO: AddressVO?) {
        if (addressVO == null) {
            return
        }
        if (BaseApplication.baseDBHelper != null) {
            BaseApplication.baseDBHelper.deleteAddress(addressVO.address)
        }
    }

    /*得到存储的所有的钱包信息*/
    private fun getWalletsAddressesFromDB(): List<AddressVO>? {
        return if (BaseApplication.baseDBHelper != null) {
            BaseApplication.baseDBHelper.queryAddress()
        } else null
    }
}