package com.fashol.seller.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.orderdata.OrderListDataModel
import com.fashol.seller.data.model.productdata.ProductDetailsDataModel

class VariantListAdapter{}
// ArrayAdapter<VariantListAdapter.MyViewHolder>() {
//    private var orderList: ArrayList<ProductDetailsDataModel.Result.Variant> = ArrayList()
//
//    fun submitList(list: List<ProductDetailsDataModel.Result.Variant>){
//        orderList = list as ArrayList<ProductDetailsDataModel.Result.Variant>
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val order = orderList[position]
//
//        holder.orderId.text = order.order_no
//        holder.customerName.text = order.customer.name
//        holder.tPrice.text = order.status
//    }
//
//    override fun getItemCount(): Int {
//        return orderList.size
//    }
//
//    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var orderId: TextView = view.findViewById(R.id.tvOrderNumber)
//        var customerName: TextView = view.findViewById(R.id.tvCustomerName)
//        var tPrice: TextView = view.findViewById(R.id.tvPrice)
//    }
//
//
//}