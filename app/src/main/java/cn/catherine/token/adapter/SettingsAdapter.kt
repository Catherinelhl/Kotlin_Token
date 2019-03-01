package cn.catherine.token.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cn.catherine.token.R
import cn.catherine.token.bean.SettingsBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.listener.OnItemSelectListener
import cn.catherine.token.tool.ListTool

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/28 10:46
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.adapter
+--------------+---------------------------------
+ description  +   「設置」頁面所有的選項數據填充顯示的適配器
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class SettingsAdapter(private val context: Context, private val settingTypes: List<SettingsBean>) :
    RecyclerView.Adapter<SettingsAdapter.viewHolder>() {

    private var settingItemSelectListener: OnItemSelectListener? = null
    fun setSettingItemSelectListener(settingItemSelectListener: OnItemSelectListener) {
        this.settingItemSelectListener = settingItemSelectListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setting, viewGroup, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, i: Int) {
        if (ListTool.isEmpty(settingTypes)) {
            return
        }
        val types = settingTypes[i] ?: return
        val type = types.type
        val tag = types.tag
        viewHolder.vLine.visibility = View.VISIBLE
        when (tag) {
            Constants.SettingType.CHECK_WALLET_INFO -> viewHolder.ivIcon.setImageResource(R.mipmap.icon_check_wallet_info)
            Constants.SettingType.MODIFY_AUTH -> viewHolder.ivIcon.setImageResource(R.mipmap.icon_modify_representative)
            Constants.SettingType.ADDRESS_MANAGE -> viewHolder.ivIcon.setImageResource(R.mipmap.icon_address_management)
            Constants.SettingType.LANGUAGE_SWITCHING -> {
                viewHolder.ivIcon.setImageResource(R.mipmap.icon_switch)
                viewHolder.vLine.visibility = View.INVISIBLE
            }
        }
        viewHolder.tvSettingType.text = type
        viewHolder.ibDetail.setOnClickListener {
            settingItemSelectListener!!.onItemSelect(
                types,
                MessageConstants.Empty
            )
        }
        viewHolder.rlSettingTypes.setOnClickListener { v ->
            settingItemSelectListener!!.onItemSelect(
                types,
                MessageConstants.Empty
            )
        }

    }

    override fun getItemCount(): Int {
        return settingTypes.size
    }


    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSettingType: TextView
        var ibDetail: ImageButton
        var ivIcon: ImageView
        var rlSettingTypes: RelativeLayout
        var vLine: View

        init {
            vLine = view.findViewById(R.id.v_line)
            tvSettingType = view.findViewById(R.id.tv_setting_type)
            ibDetail = view.findViewById(R.id.ib_detail)
            rlSettingTypes = view.findViewById(R.id.rl_setting_types)
            ivIcon = view.findViewById(R.id.iv_icon)
        }
    }
}