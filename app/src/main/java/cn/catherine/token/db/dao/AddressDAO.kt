package cn.catherine.token.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import cn.catherine.token.constant.DBConstants
import cn.catherine.token.db.vo.AddressVO
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.StringTool
import java.util.ArrayList

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 17:16
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.db.dao
+--------------+---------------------------------
+ description  +   用于对BcaasAddress数据表的操作
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class AddressDAO {
    private val TAG = AddressDAO::class.java!!.simpleName

    //BCAAS_Address table
    private val TABLE_NAME = DBConstants.BCAAS_ADDRESS//当前存储的地址信息
    private val COLUMN_UID = DBConstants.UID
    private val COLUMN_ADDRESS_NAME = DBConstants.ADDRESS_NAME
    private val COLUMN_ADDRESS = DBConstants.ADDRESS
    private val COLUMN_CREATE_TIME = DBConstants.CREATE_TIME
    //创建存储地址表的语句
    private val TABLE_BCAAS_ADDRESS = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
            " uid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            " addressName TEXT NOT NULL," +
            " address TEXT NOT NULL, " +
            " createTime DATETIME DEFAULT CURRENT_TIMESTAMP ) "


    constructor(database: SQLiteDatabase?) {
        if (database != null) {
            database!!.execSQL(TABLE_BCAAS_ADDRESS)

        }
    }

    fun onUpgrade(): String {
        return "DROP TABLE IF EXISTS $TABLE_NAME"
    }


    //----------------操作Address数据表------start------------------------------

    /**
     * 插入Address
     *
     * @param addressVO
     * @return
     */
    fun insertAddress(sqliteDatabase: SQLiteDatabase, addressVO: AddressVO?): Long {
        if (addressVO != null) {
            val values = ContentValues()
            values.put(COLUMN_ADDRESS, addressVO!!.address)
            values.put(COLUMN_ADDRESS_NAME, addressVO!!.addressName)
            val rowId = sqliteDatabase.insert(TABLE_NAME, null, values)
            sqliteDatabase.close()
            return rowId
        }
        return 0
    }

    /**
     * 查询当前所有的地址信息
     *
     * @return
     */
    fun queryAddress(sqliteDatabase: SQLiteDatabase?): List<AddressVO> {
        val addressVOS = ArrayList<AddressVO>()
        /*SELECT * FROM BcaasAddress ORDER BY uid DESC;
                SELECT * FROM BcaasAddress ORDER BY uid DESC LIMIT 0, 50;*/
        val sql = "select * from $TABLE_NAME ORDER BY $COLUMN_UID DESC"
        var cursor: Cursor? = null
        try {
            cursor = sqliteDatabase!!.rawQuery(sql, null)
            if (cursor!!.count > 0) {
                while (cursor!!.moveToNext())
                // 判断Cursor中是否有数据
                {
                    val uid = cursor!!.getInt(cursor!!.getColumnIndex(COLUMN_UID))
                    val createTime = cursor!!.getLong(cursor!!.getColumnIndex(COLUMN_CREATE_TIME))
                    val addressName = cursor!!.getString(cursor!!.getColumnIndex(COLUMN_ADDRESS_NAME))
                    val address = cursor!!.getString(cursor!!.getColumnIndex(COLUMN_ADDRESS))
                    val addressVO = AddressVO(uid, createTime, address, addressName)
                    addressVOS.add(addressVO)
                }
            }
        } catch (e: Exception) {
            if (cursor != null) {
                cursor!!.close()
            }
            sqliteDatabase!!.close()
            return addressVOS
        }

        if (cursor != null) {
            cursor!!.close()
        }
        if (sqliteDatabase != null) {
            sqliteDatabase!!.close()
        }
        return addressVOS// 如果没有数据，则返回null
    }

    /**
     * 清空Address这张表的数据，用于开发者测试用
     */
    fun clearAddress(sqliteDatabase: SQLiteDatabase) {
        val sql = "delete from $TABLE_NAME"
        LogTool.d(TAG, sql)
        sqliteDatabase.execSQL(sql)
        sqliteDatabase.close()
    }

    /**
     * 根据传入的钱包地址从数据库里面删除相对应的数据
     *
     * @param address
     */
    fun deleteAddress(sqliteDatabase: SQLiteDatabase, address: String) {
        val sql = "delete from $TABLE_NAME where $COLUMN_ADDRESS ='$address'"
        LogTool.d(TAG, sql)
        sqliteDatabase.execSQL(sql)
        sqliteDatabase.close()
    }

    /**
     * 判斷當前是否有重複的地址活著命名信息
     *
     * @param sqLiteDatabase
     * @param addressVo
     * @return 返回int，表示當前的狀態
     *
     *
     * 0：代表空數據
     * 1：代表地址命名重複
     * 2：代表地址重複
     * -1：代表不重複
     */
    fun queryIsExistAddress(sqLiteDatabase: SQLiteDatabase, addressVo: AddressVO?): Int {
        val address: String
        val addressName: String
        var status = 0
        if (addressVo == null) {
            return status//返回存在，不进行存储
        }
        //取得当前存储的地址信息
        address = addressVo!!.address
        //取得当前存储的地址名字
        addressName = addressVo!!.addressName
        if ((StringTool.isEmpty(address) || StringTool.isEmpty(addressName))) {
            return status
        }
        //查询当前是否有命名相同的数据
        if (checkRepeatData(sqLiteDatabase, COLUMN_ADDRESS_NAME, addressName)) {
            status = 1
        } else {
            //查询当前是否有地址相同
            val exist = checkRepeatData(sqLiteDatabase, COLUMN_ADDRESS, address)
            if (exist) {
                status = 2
            } else {
                status = -1
            }
        }
        sqLiteDatabase.close()
        return status
    }

    /**
     * 檢查重複數據
     *
     * @param sqLiteDatabase
     * @param columnName
     * @param dataName
     */
    private fun checkRepeatData(sqLiteDatabase: SQLiteDatabase, columnName: String, dataName: String): Boolean {
        //查询当前是否有命名相同的数据
        val sql = "select count(*) from $TABLE_NAME where $columnName ='$dataName'"
        var exist = false
        var cursor: Cursor? = null
        try {
            cursor = sqLiteDatabase.rawQuery(sql, null)
            if (cursor!!.moveToNext())
            // 判断Cursor中是否有数据
            {
                exist = cursor!!.getInt(0) != 0
            }
        } catch (e: Exception) {
            exist = false
        }

        cursor!!.close()
        return exist
    }
    //----------------操作Address数据表------end--------------------------------
}