package com.example.testdemo.ui.servicelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.shadowsocks.R

class ServiceListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)
//        initRecyclerView()
    }

//    private fun initRecyclerView() {
//        mAdapter = OrderListAdapter(listData)
//        binding.layoutManager = LinearLayoutManager(this)
//        binding.adapter = mAdapter
//        val decor =
//            DividerItemDecoration(this, (binding.layoutManager as LinearLayoutManager).orientation)
//        ContextCompat.getDrawable(this, R.drawable.recycler_item_divider)
//            ?.let { decor.setDivider(it) }
//        binding.recyclerView.addItemDecoration(decor)
//        mAdapter.setOnItemClickListener { _, _, position ->
//            val bean: OrderListInfo.DataBean = listData[position]
//            //待付款&待提货
//            val intent =
//                if ((bean.orderStatus == 1 || bean.orderStatus == 16) && bean.afterSaleStatus != 1) {
//                    //保存订单列表
//                    Intent(this, OrderWriteOffActivity().javaClass)
//                } else {
//                    Intent(this, OrderDetailActivity().javaClass)
//                }
//            val dataJson = GsonUtil.bean2String(listData[position])
//            intent.putExtra("listData", dataJson)
//            intent.putExtra("orderId", bean.id)
//            //售后id置空
//            MmkvUtils.set(Constant.AFTER_SALES_ID, "")
//            startActivity(intent)
//        }
//        mAdapter.setOnItemChildClickListener { _, view, position ->
//            when (view.id) {
//                //发货
//                R.id.item_deliver_goods_btn -> deliverClick(position)
//                //主动退款
//                R.id.item_active_refund_btn -> activeRefundClick(position)
//                //核销
//                R.id.item_write_off_btn -> writeOffClick(position)
//                R.id.item_order_update_btn -> updateClick(position)
//                //更多（驳回，同意退款）
//                R.id.text_popup_more -> morePopupClick(view, position)
//                R.id.item_order_reject_btn -> rejectClick(position)
//                R.id.item_order_cancel_btn -> cancelClick(position)
//                R.id.item_order_refund_btn -> refundClick(position)
//                R.id.item_verification_code_verification_btn -> verificationCodeVerification(
//                    position
//                )
//                R.id.item_scan_write_off_btn -> scanAndWriteOff()
//            }
//        }
//        binding.refreshLayout.setOnRefreshListener {
//            viewModel.map["pageNum"] = 1
//            viewModel.getOrderListData()
//        }
//        //上拉加载更多
//        binding.refreshLayout.setOnLoadMoreListener {
//            var page: Int = Integer.valueOf(viewModel.map["pageNum"].toString())
//            viewModel.map["pageNum"] = ++page
//            viewModel.getOrderListData()
//        }
//        radio_group.setOnCheckedChangeListener { group, checkedId ->
//            val type = when (checkedId) {
//                R.id.radio_button0 -> "SELF_TAKE_ORDER"
//                R.id.radio_button1 -> "SEND_ORDER"
////                R.id.radio_button2 -> 2
//                else -> "SELF_TAKE_ORDER"
//            }
////            viewModel.map["multiOrderType"] = type
//            viewModel.map["multiOrderTabEnum"] = type
//            viewModel.map["pageNum"] = 1
//            tabTitleChange(checkedId)
//
//        }
//    }
}