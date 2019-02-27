package cn.catherine.token.ui.frg

import android.os.Bundle
import android.view.View
import cn.catherine.token.R
import cn.catherine.token.base.BaseFragment
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.StringTool
import com.obt.qrcode.encoding.EncodingUtils
import kotlinx.android.synthetic.main.frg_receive.*

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/27 14:25
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.ui.frg
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class ReceiveFragment : BaseFragment() {

    //二維碼渲染的前景色
    private val foregroundColorOfQRCode = -0x1000000
    //二維碼渲染的背景色
    private val backgroundColorOfQRCode = 0x00000000

    override fun getLayoutRes(): Int = R.layout.frg_receive

    override fun initViews(view: View) {
        val address = GlobalVariableManager.getWalletAddress()
        if (StringTool.isEmpty(address)) {
            showToast(resources.getString(R.string.account_data_error))
        } else {
            tv_my_address.text = address
            makeQRCodeByAddress(address!!)
        }

    }

    override fun getArgs(bundle: Bundle) {
    }

    override fun initListener() {
    }

    fun refreshView() {

    }

    private fun makeQRCodeByAddress(address: String) {
        //        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        val qrCode = EncodingUtils.createQRCode(
            address,
            frgContext.resources.getDimensionPixelOffset(R.dimen.x200),
            frgContext.resources.getDimensionPixelOffset(R.dimen.x200),
            null,
            foregroundColorOfQRCode,
            backgroundColorOfQRCode
        )
        iv_qr_code.setImageBitmap(qrCode)
    }

}