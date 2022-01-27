package com.fashol.seller.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.productdata.CartItemDataModel

class CartAdapter : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    private var itemList: ArrayList<CartItemDataModel> = ArrayList()

    fun submitList(list: List<CartItemDataModel>){
        itemList = list as ArrayList<CartItemDataModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]

        holder.pName.text = item.name
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView = view.findViewById(R.id.ivCartProductAvatar)
        var pName: TextView = view.findViewById(R.id.tvCartProductName)
    }

}