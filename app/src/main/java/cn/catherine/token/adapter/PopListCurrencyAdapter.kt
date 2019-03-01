package cn.catherine.token.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.catherine.token.R
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.listener.OnCurrencyItemSelectListener
import cn.catherine.token.manager.GlobalVariableManager
import cn.catherine.token.tool.ListTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.vo.PublicUnitVO

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 17:53
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.adapter
+--------------+---------------------------------
+ description  +   用於顯示已經存在的所有幣種數據填充在PopWindow裡的適配器
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class PopListCurrencyAdapter(private val context: Context, private var publicUnitVOS: List<PublicUnitVO>?) :
    RecyclerView.Adapter<PopListCurrencyAdapter.viewHolder>() {
    private val TAG = PopListCurrencyAdapter::class.java.simpleName
    private var onCurrencyItemSelectListener: OnCurrencyItemSelectListener? = null


    /**
     * 更新数据
     */
    fun refreshList(list: List<PublicUnitVO>?) {
        this.publicUnitVOS = list
        notifyDataSetChanged()
    }

    fun setOnItemSelectListener(onCurrencyItemSelectListener: OnCurrencyItemSelectListener) {
        this.onCurrencyItemSelectListener = onCurrencyItemSelectListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pop_currency_list, viewGroup, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, i: Int) {
        if (ListTool.isEmpty(publicUnitVOS)) return
        val content = publicUnitVOS!![i].blockService
        if (StringTool.isEmpty(content)) {
            return
        }
        //标记当前默认的币种
        viewHolder.tvContent.setTextColor(
            context.resources.getColor(
                if (StringTool.equals(content, GlobalVariableManager.blockService))
                    R.color.orange_yellow
                else
                    R.color.black_1d2124
            )
        )
        viewHolder.vLine.visibility = if (i == publicUnitVOS!!.size - 1) View.INVISIBLE else View.VISIBLE
        viewHolder.tvContent.text = content
        viewHolder.itemView.setOnClickListener {
            onCurrencyItemSelectListener!!.onItemSelect(
                content,
                MessageConstants.Empty
            )
        }

    }

    override fun getItemCount(): Int {
        return if (ListTool.isEmpty(publicUnitVOS)) 0 else publicUnitVOS!!.size
    }

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvContent: TextView
        val vLine: View

        init {
            tvContent = view.findViewById(R.id.tv_content)
            vLine = view.findViewById(R.id.v_line)
        }
    }
}
