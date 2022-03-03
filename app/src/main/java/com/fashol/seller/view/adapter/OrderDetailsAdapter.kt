package com.fashol.seller.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.orderdata.OrderDetailsResponseDataModel

class OrderDetailsAdapter: RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder>() {

    private var orderList: ArrayList<OrderDetailsResponseDataModel.Result.Order> = ArrayList()

    fun submitList(list: List<OrderDetailsResponseDataModel.Result.Order>){
        orderList = list as ArrayList<OrderDetailsResponseDataModel.Result.Order>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_single_order_product_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderDetailsAdapter.MyViewHolder, position: Int) {
        val item = orderList[position]

        holder.itemName.text = item.product.name
        holder.itemQ.text = item.quantity
        holder.itemNo.text = position.toString()
        holder.itemV.text = item.variant.name
        holder.itemP.text = item.per_price.toString()
        holder.itemTotalP.text = item.total_price.toString()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemNo: TextView = view.findViewById(R.id.tvItemNumber)
        var itemName: TextView = view.findViewById(R.id.tvItemName)
        var itemV: TextView = view.findViewById(R.id.tvItemVariant)
        var itemQ: TextView = view.findViewById(R.id.tvItemQuantity)
        var itemP: TextView = view.findViewById(R.id.tvItemPrice)
        var itemTotalP: TextView = view.findViewById(R.id.tvItemTotalPrice)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}