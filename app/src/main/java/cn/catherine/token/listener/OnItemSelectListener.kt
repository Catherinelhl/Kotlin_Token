package cn.catherine.token.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 11:00
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.listener
+--------------+---------------------------------
+ description  +   回調監聽：各種list彈框、多類別條目选择器監聽回調響應
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface OnItemSelectListener {
    /**
     * 返回當前選擇欄目的數據類
     *
     * @param from 用於區分「語言切換」、「幣種切換」
     */
    abstract fun <T> onItemSelect(type: T, from: String)

    // 是否改变了item的选择
    abstract fun changeItem(isChange: Boolean)
}