package cn.catherine.token.ui.aty

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.LOLLIPOP_MR1
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import cn.catherine.token.R
import cn.catherine.token.base.BaseActivity
import cn.catherine.token.constant.Constants
import cn.catherine.token.manager.AppManager
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.SoftKeyBoardTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.ecc.WalletTool
import com.jakewharton.rxbinding2.view.RxView
import com.obt.qrcode.activity.CaptureActivity
import kotlinx.android.synthetic.main.aty_import.*
import kotlinx.android.synthetic.main.include_header.*
import java.util.concurrent.TimeUnit

/*
+
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/20 17:27
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.aty
+--------------+---------------------------------
+ description  +   导入钱包
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
+
*/

class ImportWalletActivity : BaseActivity() {
    // 跳轉拍照
    private val codeOfCapture = 0x11
    //跳轉設置密碼
    private val codeOfSetPassword = 0x12

    override fun getArgs(bundle: Bundle) {
    }

    override fun getLayoutRes(): Int = R.layout.aty_import

    override fun initViews() {
        tv_title.text = resources.getString(R.string.import_wallet)
        ib_back.visibility = View.VISIBLE
    }

    override fun initData() {
    }

    override fun initListener() {
        rl_import_wallet.setOnTouchListener { v, event ->
            activity?.let {
                SoftKeyBoardTool(it).hideSoftKeyboard()
            }
            false
        }
        rl_private_key.setOnTouchListener { v, event -> true }
        ib_back.setOnClickListener { v -> AppManager(activity).setResult(true) }
        val subscribeSure = RxView.clicks(btn_sure)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe { o ->
                activity?.let {
                    SoftKeyBoardTool(it).hideSoftKeyboard()
                }
                val privateKey = et_private_key.text.toString()
                if (StringTool.isEmpty(privateKey)) {
                    showToast(resources.getString(R.string.enter_private_key))
                } else {
                    if (WalletTool().parseWIFPrivateKey(privateKey)) {
                        startActivityForResult(
                            Intent(this, SetPasswordForImportWalletActivity::class.java), codeOfSetPassword
                        )
                    } else {
                        showToast(getString(R.string.private_key_error))
                    }
                }

            }
        val subscribeScan = RxView.clicks(ib_scan)
            .throttleFirst(Constants.Time.sleep800, TimeUnit.MILLISECONDS)
            .subscribe { o -> getCameraPermission() }
    }

    /*獲得照相機權限*/
    private fun getCameraPermission() {
        if (Build.VERSION.SDK_INT > LOLLIPOP_MR1) {//这个说明系统版本在6.0之下，不需要动态获取权限
            if (ContextCompat.checkSelfPermission(
                    this@ImportWalletActivity,
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(
                    this@ImportWalletActivity,
                    arrayOf(android.Manifest.permission.CAMERA), Constants.REQUEST_CODE_CAMERA_OK
                )

            } else {
                //说明已经获取到摄像头权限了 想干嘛干嘛
                intentToCaptureActivity()

            }
        }
    }

    /**
     *跳转调用拍照的Activity
     */
    private fun intentToCaptureActivity() {
        startActivityForResult(Intent(this, CaptureActivity::class.java), codeOfCapture)

    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return
            }
            if (requestCode == codeOfCapture) {
                // 跳轉「拍照」返回
                val bundle = data.extras
                if (bundle != null) {
                    val result = bundle.getString(Constants.RESULT)
                    et_private_key.setText(result)
                    result?.let {
                        et_private_key.setSelection(result!!.length)
                    }
                }
            } else if (requestCode == codeOfSetPassword) {
                //跳轉「為導入的錢包設置密碼」返回
                val bundle = data.extras
                if (bundle != null) {
                    val isBack = bundle.getBoolean(Constants.KeyMaps.From)
                    LogTool.d(tag, isBack)
                    if (!isBack) {
                        activity?.let {
                            //點擊「導入」按鈕，那麼應該關閉當前頁面，然後進行登錄
                            AppManager(activity).setResult(false)
                        }

                    }
                }
            }
        }
    }
}