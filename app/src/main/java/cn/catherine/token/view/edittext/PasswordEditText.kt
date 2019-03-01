package cn.catherine.token.view.edittext

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import cn.catherine.token.R
import cn.catherine.token.constant.Constants
import cn.catherine.token.listener.PasswordWatcherListener
import cn.catherine.token.tool.StringTool
import kotlinx.android.synthetic.main.aty_set_pwd_for_import_wallet.view.*
import kotlinx.android.synthetic.main.include_edit_text_password.view.*
import kotlinx.android.synthetic.main.layout_password_edittext.view.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/1 15:03
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.view.edittext
+--------------+---------------------------------
+ description  +   自定義EditText：Phone版密码输入框
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class PasswordEditText(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    /*声明需要显示的标题以及hint*/
    private var title: String? = null
    private var hint: String? = null
    /*是否需要暗示标题或者hint，默认是显示，若果不需要显示，则需要重新赋值*/
    private var showTitle: Boolean = false
    private var showHint: Boolean = false
    /*監聽當前密碼的輸入*/
    var passwordWatcherListener: PasswordWatcherListener? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_password_edittext, this, true)
        //获取自定义属性的值
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.privateKeyStyle)
        if (typedArray != null) {
            title = typedArray.getString(R.styleable.privateKeyStyle_title)
            hint = typedArray.getString(R.styleable.privateKeyStyle_hint)
            showHint = typedArray.getBoolean(R.styleable.privateKeyStyle_showHint, true)
            showTitle = typedArray.getBoolean(R.styleable.privateKeyStyle_showTitle, true)
            val showLine = typedArray.getBoolean(R.styleable.privateKeyStyle_showLine, true)
            val textColor = typedArray.getInteger(
                R.styleable.privateKeyStyle_textColor,
                context.resources.getColor(R.color.black_1d2124)
            )
            val hintColor = typedArray.getInteger(
                R.styleable.privateKeyStyle_hintColor,
                context.resources.getColor(R.color.black30_1d2124)
            )


            typedArray.recycle()
            title?.let {
                tv_et_title.text = it
            }
            hint?.let {
                et_password.hint = it
            }
            v_password_line.visibility = if (showLine) View.VISIBLE else View.INVISIBLE
            tv_et_title.visibility = if (showTitle) View.VISIBLE else View.GONE
            et_password.setTextColor(textColor)
            tv_et_title.setTextColor(textColor)
            et_password.setHintTextColor(hintColor)
        }
    }


    fun initListener() {
        cb_pwd.setOnCheckedChangeListener { buttonView, isChecked ->
            val text = et_password.text.toString()

            if (StringTool.isEmpty(text)) {
                return@setOnCheckedChangeListener
            }
            et_password.inputType = if (isChecked)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD//设置当前私钥显示不可见


        }
        et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    val password = s.toString()
                    if (StringTool.notEmpty(password)) {
                        if (password.length >= Constants.PASSWORD_MIN_LENGTH) {
                            passwordWatcherListener?.let {
                                it.onComplete(password)
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    //返回私钥文本
    fun getPassword(): String? {
        return if (et_password == null) {
            null
        } else et_password.text.toString()
    }

    //私钥文本
    fun setPassword(privateKey: String) {
        et_password.setText(privateKey)
    }

}