package com.example.testdemo.ui.servicelist

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.testdemo.bean.ProfileBean
import com.github.shadowsocks.R


class ServiceListAdapter(data: List<ProfileBean>?) :
    BaseQuickAdapter<ProfileBean, BaseViewHolder>(
        R.layout.item_service,
        data as MutableList<ProfileBean>?
    ) {
    override fun convert(holder: BaseViewHolder, item: ProfileBean) {
        holder.setText(R.id.tv_service_name,item.ufo_country+"-"+item.ufo_city)
    }

}
