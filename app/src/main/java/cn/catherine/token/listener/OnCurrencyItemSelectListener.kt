package cn.catherine.token.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 11:05
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.listener
+--------------+---------------------------------
+ description  +   回調監聽：币种條目选择器監聽回調響應
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface OnCurrencyItemSelectListener {
    /**
     * 返回當前選擇欄目的數據類
     * 「幣種切換」
     * //     *
     * //     * @param From     用以区分是从何处点击的币种选择
     * //     * @param isChange 是否改变了item的选择
     * //     *                 , String From, boolean isChange)
     */
    abstract fun <T> onItemSelect(type: T, from: String?)
}