package cn.catherine.token.view.pop

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import cn.catherine.token.R
import cn.catherine.token.adapter.PopListCurrencyAdapter
import cn.catherine.token.event.RefreshBlockServiceEvent
import cn.catherine.token.listener.OnCurrencyItemSelectListener
import cn.catherine.token.tool.OttoTool
import cn.catherine.token.tool.ecc.WalletTool
import com.squareup.otto.Subscribe

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 17:03
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.view.dialog
+--------------+---------------------------------
+ description  +   自定義PopWindow：显示「幣種」列表,点击显示，会比对当前默认的币种，然后标记出当前的默认的币种
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class BlockServicesPopWindow(private val context: Context) : PopupWindow(context) {

    private val TAG = BlockServicesPopWindow::class.java.simpleName
    private var popWindow: View
    private var recyclerView: RecyclerView//显示当前列表
    private var onCurrencyItemSelectListener: OnCurrencyItemSelectListener? = null
    //标记来自于哪里
    private var fromWhere: String? = null
    private var adapter: PopListCurrencyAdapter? = null

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        popWindow = inflater.inflate(R.layout.popwindow_blockservices, null)
        contentView = popWindow
        recyclerView = popWindow.findViewById(R.id.rv_list)
        OttoTool.register(this)
    }

    fun addCurrencyList(onCurrencyItemSelectListener: OnCurrencyItemSelectListener, from: String) {
        this.onCurrencyItemSelectListener = onCurrencyItemSelectListener
        this.fromWhere = from
        val list = WalletTool().getPublicUnitVO()
        adapter = PopListCurrencyAdapter(context, list)
        adapter!!.setOnItemSelectListener(currencyItemSelectListener)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

    }

    private val currencyItemSelectListener = object : OnCurrencyItemSelectListener {
        override fun <T> onItemSelect(type: T, from: String?) {
            dismiss()
            onCurrencyItemSelectListener!!.onItemSelect<T>(type, fromWhere)
        }
    }

    /**
     * 更新币种信息
     */
    @Subscribe
    fun refreshBlockService(refreshBlockServiceEvent: RefreshBlockServiceEvent) {
        val list = WalletTool().getPublicUnitVO()
        if (adapter != null) {
            adapter!!.refreshList(list)
        }
    }
}