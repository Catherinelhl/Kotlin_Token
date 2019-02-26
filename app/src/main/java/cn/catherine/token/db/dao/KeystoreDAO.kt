package cn.catherine.token.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import cn.catherine.token.constant.DBConstants
import cn.catherine.token.tool.LogTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 17:17
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.db.dao
+--------------+---------------------------------
+ description  +   用于对BcaasKeystore数据表的操作
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class KeystoreDAO {
    private val TAG = KeystoreDAO::class.java!!.simpleName

    //BCAAS_KEYSTORE table
    private val TABLE_NAME = DBConstants.BCAAS_SECRET_KEY//当前存储的钱包信息
    private val COLUMN_UID = DBConstants.UID
    private val COLUMN_KEYSTORE = DBConstants.KEYSTORE
    private val COLUMN_CREATETIME = DBConstants.CREATE_TIME
    //创建存储钱包表的语句
    private val TABLE_BCAAS_KEYSTORE_CREATE = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
            " uid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            " keyStore TEXT NOT NULL ," +
            " createTime DATETIME DEFAULT CURRENT_TIMESTAMP ) "

    constructor(database: SQLiteDatabase?) {
        if (database != null) {
            database!!.execSQL(TABLE_BCAAS_KEYSTORE_CREATE)

        }
    }

    /**
     * 更新表格
     *
     * @return
     */
    fun onUpgrade(): String {
        return "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    //----------------操作Keystore数据表------start------------------------------

    /**
     * 插入Keystore信息
     *
     * @param keyStore
     * @return
     */
    fun insertKeyStore(sqliteDatabase: SQLiteDatabase, keyStore: String): Long {
        val values = ContentValues()
        values.put(COLUMN_KEYSTORE, keyStore)
        val rowId = sqliteDatabase.insert(TABLE_NAME, null, values)
        sqliteDatabase.close()
        return rowId
    }

    /**
     * 查询当前表中是否有数据
     *
     * @return
     */
    fun queryIsExistKeyStore(sqliteDatabase: SQLiteDatabase): Boolean {
        var exist = false
        val sql = "select count(*) from $TABLE_NAME"
        var cursor: Cursor? = null
        try {
            cursor = sqliteDatabase.rawQuery(sql, null)
            if (cursor!!.moveToNext())
            // 判断Cursor中是否有数据
            {
                exist = cursor!!.getInt(0) != 0
                LogTool.d(TAG, exist)// 返回总记录数
            }
        } catch (e: Exception) {
            exist = false
            cursor!!.close()
            sqliteDatabase.close()
            return exist
        }

        cursor!!.close()
        sqliteDatabase.close()
        return exist// 如果没有数据，则返回0

    }


    /**
     * 更新 keystore信息：替换钱包
     *
     * @param keystore
     */
    fun updateKeyStore(sqliteDatabase: SQLiteDatabase, keystore: String) {
        //1：查询当前表中是否有其他数据，有的话，就进行删除
        val keystoreOld = queryKeyStore(sqliteDatabase, false)
        LogTool.d(TAG, "即将删除旧数据：" + keystoreOld!!)
        //+ " where " + COLUMN_KEYSTORE + " = " + keystoreOld
        //既然当前数据库只有一条数据，那么可以就全部替换。
        val sql = "update $TABLE_NAME set $COLUMN_KEYSTORE ='$keystore' where 1=1"
        LogTool.d(TAG, sql)
        sqliteDatabase.execSQL(sql)
        sqliteDatabase.close()
    }

    /**
     * 查询当前已存在的keystore信息
     *
     * @return
     */
    fun queryKeyStore(sqliteDatabase: SQLiteDatabase?, isCloseSqlLite: Boolean): String? {
        var keystore: String? = null
        val sql = "select * from $TABLE_NAME ORDER BY $COLUMN_KEYSTORE DESC LIMIT 1"
        var cursor: Cursor? = null
        try {
            cursor = sqliteDatabase!!.rawQuery(sql, null)
            if (cursor!!.count > 0) {
                if (cursor!!.moveToFirst())
                // 判断Cursor中是否有数据
                {
                    keystore = cursor!!.getString(cursor!!.getColumnIndex(COLUMN_KEYSTORE))
                    LogTool.d(TAG, keystore)
                }
            }
        } catch (e: Exception) {
            if (cursor != null) {
                cursor!!.close()

            }
            if (sqliteDatabase != null) {
                sqliteDatabase!!.close()

            }
            return keystore
        }

        if (isCloseSqlLite) {
            if (cursor != null) {
                cursor!!.close()
            }
            if (sqliteDatabase != null) {
                sqliteDatabase!!.close()

            }
        }
        return keystore// 如果没有数据，则返回null
    }

    //----------------操作Keystore数据表------end------------------------------

}