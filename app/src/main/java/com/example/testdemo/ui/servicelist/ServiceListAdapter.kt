package com.example.testdemo.ui.servicelist

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.testdemo.bean.ProfileBean
import com.github.shadowsocks.R


class ServiceListAdapter(data: List<ProfileBean.SafeLocation>?) :
    BaseQuickAdapter<ProfileBean.SafeLocation, BaseViewHolder>(
        R.layout.item_service,
        data as MutableList<ProfileBean.SafeLocation>?
    ) {
    override fun convert(holder: BaseViewHolder, item: ProfileBean.SafeLocation) {
        holder.setText(R.id.tv_service_name,item.ufo_country+"-"+item.ufo_city)
        if(item.cheek_state == true){
            holder.setImageResource(R.id.img_state, R.mipmap.rd_check)
        }else{
            holder.setImageResource(R.id.img_state, R.mipmap.rd_uncheck)
        }
//        holder.setImageResource(R.id.img_service_icon,)
    }
    /**
     *加载国旗
     */
//    private loadFlag(flag:String):Int{
//        when(flag){
//            ""
//        }
//    }
}
