package cn.catherine.token.listener

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/14 16:25
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.listener
+--------------+---------------------------------
+ description  +   检查当前权限回掉监听
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface PermissionListener {
    //已经获取到权限
    fun getPermissionSuccess()

    //获取权限失败
    fun getPermissionFailure()
}