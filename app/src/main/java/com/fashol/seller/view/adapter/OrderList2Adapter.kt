package com.fashol.seller.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.orderdata.OrderListDataModel

class OrderList2Adapter(private var onItemClickListener: OnOrderClickListener) : RecyclerView.Adapter<OrderList2Adapter.MyViewHolder>() {
    private var orderList: ArrayList<OrderListDataModel.Result> = ArrayList()

    fun submitList(list: List<OrderListDataModel.Result>){
        orderList = list as ArrayList<OrderListDataModel.Result>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_order_item_all_order, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = orderList[position]

        holder.orderId.text = "Order ID: " + order.order_no
        holder.customerName.text = order.customer.name
        holder.customerAddress.text = order.customer.store_address
        holder.status.text = order.status
        val s = order.order_date.split('T')
        holder.date.text = s[0]
        holder.quantity.text = "No of Items: " + order.order_list.size.toString()

//        if(order.status == "pending")
//            holder.status.text = order.status
//        else if(order.status == "pending"){
//            holder.status.text = order.status
//        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onOrderClickListener(order.order_list[0].order_id)
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var orderId: TextView = view.findViewById(R.id.tvOrderId)
        var customerName: TextView = view.findViewById(R.id.tvCustomerName)
        var customerAddress: TextView = view.findViewById(R.id.tvCustomerAddress)
        var date: TextView = view.findViewById(R.id.tvDate)
        var quantity: TextView = view.findViewById(R.id.tvQuantity)
        var status: TextView = view.findViewById(R.id.tvOrderStatus)
    }

    interface OnOrderClickListener{
        fun onOrderClickListener(id: Int)
    }

}