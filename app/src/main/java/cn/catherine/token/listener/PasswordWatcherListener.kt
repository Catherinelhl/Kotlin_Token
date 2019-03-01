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
+ description  +   回調監聽：用於監聽密碼輸入框的輸入完成
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

interface PasswordWatcherListener {
    abstract fun onComplete(password: String)

}