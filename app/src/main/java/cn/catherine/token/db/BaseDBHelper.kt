package cn.catherine.token.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import cn.catherine.token.constant.DBConstants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.db.dao.AddressDAO
import cn.catherine.token.db.dao.KeystoreDAO
import cn.catherine.token.db.vo.AddressVO
import cn.catherine.token.tool.LogTool
import java.util.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 17:18
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.db
+--------------+---------------------------------
+ description  +  數據庫： 1：存儲當前Wallet Keystore屬性信息 2：存儲當前添加Wallet地址信息
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BaseDBHelper(context: Context)
    : SQLiteOpenHelper(context, DBConstants.DB_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1// 当前数据库的版本
    }

    private val TAG = BaseDBHelper::class.java.name
    //创建存储用户信息的数据表操作类
    private var keystoreDAO: KeystoreDAO? = null
    //创建存储钱包地址信息的数据表操作类
    private var addressDAO: AddressDAO? = null

    init {
        keystoreDAO = KeystoreDAO(writableDatabase)
        addressDAO = AddressDAO(writableDatabase)
    }

    override fun onCreate(db: SQLiteDatabase) {
        LogTool.d(TAG, "onCreate")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(keystoreDAO!!.onUpgrade())
        db.execSQL(addressDAO!!.onUpgrade())
        onCreate(db)
    }
    //----------------操作Keystore数据表------start------------------------------

    /**
     * 插入Keystore信息
     *
     * @param keyStore
     * @return
     */
    fun insertKeyStore(keyStore: String): Long {
        if (addressDAO == null) {
            return 0
        }
        //插入数据之前，可以先执行delete操作
        clearKeystore(writableDatabase)
        return keystoreDAO!!.insertKeyStore(writableDatabase, keyStore)
    }

    /**
     * 清空Keystore这张表的数据，用于开发者测试用
     */
    fun clearKeystore(sqliteDatabase: SQLiteDatabase) {
        val sql = "delete from " + DBConstants.BCAAS_SECRET_KEY
        LogTool.d(TAG, sql)
        sqliteDatabase.execSQL(sql)
        sqliteDatabase.close()
    }

    /**
     * 查询当前表中是否有数据
     *
     * @return
     */
    fun queryIsExistKeyStore(): Boolean {
        return if (keystoreDAO == null) {
            false
        } else keystoreDAO!!.queryIsExistKeyStore(writableDatabase)
// 如果没有数据，则返回0
    }


    /**
     * 更新 keystore信息：替换钱包
     *
     * @param keystore
     */
    fun updateKeyStore(keystore: String) {
        if (keystoreDAO == null) {
            return
        }
        keystoreDAO!!.updateKeyStore(getWritableDatabase(), keystore)
    }

    /**
     * 查询当前已存在的keystore信息
     *
     * @return
     */
    fun queryKeyStore(): String? {
        return if (keystoreDAO == null) {
            MessageConstants.Empty
        } else keystoreDAO!!.queryKeyStore(getWritableDatabase(), true)
// 如果没有数据，则返回null
    }
    //----------------操作Keystore数据表------end------------------------------


    //----------------操作Address数据表------start------------------------------

    /**
     * 插入Address
     *
     * @param addressVO
     * @return
     */
    fun insertAddress(addressVO: AddressVO): Long {
        return if (addressDAO == null) {
            0
        } else addressDAO!!.insertAddress(getWritableDatabase(), addressVO)
    }

    /**
     * 查询当前所有的地址信息
     *
     * @return
     */
    fun queryAddress(): List<AddressVO> {
        return if (addressDAO == null) {
            ArrayList<AddressVO>()
        } else addressDAO!!.queryAddress(getWritableDatabase())
// 如果没有数据，则返回null
    }

    /**
     * 清空Address这张表的数据，用于开发者测试用
     */
    fun clearAddress() {
        if (addressDAO == null) {
            return
        }
        addressDAO!!.clearAddress(getWritableDatabase())
    }

    /**
     * 根据传入的钱包地址从数据库里面删除相对应的数据
     *
     * @param address
     */
    fun deleteAddress(address: String) {
        if (addressDAO == null) {
            return
        }
        addressDAO!!.deleteAddress(getWritableDatabase(), address)
    }

    /**
     * 查询当前是否存储这个地址
     *
     * @param addressVo
     */
    fun queryIsExistAddress(addressVo: AddressVO): Int {
        return if (addressDAO == null) {
            -1
        } else addressDAO!!.queryIsExistAddress(getWritableDatabase(), addressVo)
    }

    //----------------操作Address数据表------end--------------------------------

}