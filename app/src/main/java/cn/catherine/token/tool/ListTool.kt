package cn.catherine.token.tool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 15:36
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool
+--------------+---------------------------------
+ description  +   工具類：List內容管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object ListTool {
    //判断当前的list是否为空
    fun <T> isEmpty(list: List<T>?): Boolean {
        if (list == null) {
            return true
        } else if (list.size == 0) {
            return true
        }
        return false

    }

    fun <T> noEmpty(list: List<T>): Boolean {
        return !isEmpty(list)

    }
}