package com.fashol.seller.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.productdata.CartItemDataModel
import com.fashol.seller.utilits.Utils
import com.squareup.picasso.Picasso

class CartAdapter : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    private var itemList: ArrayList<CartItemDataModel> = ArrayList()

    fun submitList(list: List<CartItemDataModel>){
        itemList = list as ArrayList<CartItemDataModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]

        holder.pName.text = item.name
        //holder.quantity.text = item.quantity.toString()
        holder.pVariant.text = "(" + item.variant + ")"
        holder.priceQuantity.text = "দাম: " + item.quantity + " * " + item.unitPrice + " = " + (item.quantity * item.unitPrice)  + " টাকা"
        val url = Utils.baseUrl() +  item.avatar

        // load image into view
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(holder.avatar)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView = view.findViewById(R.id.ivCartProductAvatar)
        var pName: TextView = view.findViewById(R.id.tvCartProductName)
        var pVariant: TextView = view.findViewById(R.id.tvItemVariant)
        var priceQuantity: TextView = view.findViewById(R.id.tv_cart_item_Price)
        //var quantity: TextView = view.findViewById(R.id.tvQuantity)
    }

}