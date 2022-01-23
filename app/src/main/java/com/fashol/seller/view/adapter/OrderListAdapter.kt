package com.fashol.seller.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.orderdata.OrderDataModel

class OrderListAdapter : RecyclerView.Adapter<OrderListAdapter.MyViewHolder>() {
    private var orderList: ArrayList<OrderDataModel> = ArrayList()

    fun submitList(list: List<OrderDataModel>){
        orderList = list as ArrayList<OrderDataModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_order_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = orderList[position]

        holder.orderId.text = order.orderid
        holder.customerName.text = order.customerName
        holder.tPrice.text = order.tPrice.toString()
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var orderId: TextView = view.findViewById(R.id.tvOrderNumber)
        var customerName: TextView = view.findViewById(R.id.tvCustomerName)
        var tPrice: TextView = view.findViewById(R.id.tvPrice)
    }

}