package com.fashol.seller.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.orderdata.CartItemDataModel
import com.fashol.seller.utilits.Utils
import com.squareup.picasso.Picasso

class CartAdapter(private var onItemClickListener: CartAdapter.OnCartItemClickListener) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    private var itemList: ArrayList<CartItemDataModel> = ArrayList()

    fun submitList(list: List<CartItemDataModel>){
        itemList = list as ArrayList<CartItemDataModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]

        holder.pName.text = item.name
        //holder.quantity.text = item.quantity.toString()
        holder.pVariant.text = "(" + item.variant + ")"
        holder.priceQuantity.text = "দাম: " + item.quantity + " * " + item.unitPrice + " = " + (item.quantity * item.unitPrice)  + " টাকা"
        holder.qun.text = item.quantity.toString()
        val url = Utils.baseUrl() +  item.avatar
        // load image into view
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(holder.avatar)

        holder.add.setOnClickListener {
            item.quantity = item.quantity + 1
            notifyDataSetChanged()
        }

        holder.minus.setOnClickListener {
            if(item.quantity > 0.0){
                item.quantity = item.quantity - 1
                notifyDataSetChanged()
            }
        }

        holder.remove.setOnClickListener {
            onItemClickListener.onRemoveClickListener(position, (item.unitPrice * item.quantity))
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onCartItemClickListener(position, (item.unitPrice * item.quantity))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView = view.findViewById(R.id.ivCartProductAvatar)
        var pName: TextView = view.findViewById(R.id.tvCartProductName)
        var pVariant: TextView = view.findViewById(R.id.tvItemVariant)
        var priceQuantity: TextView = view.findViewById(R.id.tv_cart_item_Price)
        var qun: TextView = view.findViewById(R.id.tvQuantity)
        var remove: TextView = view.findViewById(R.id.tv_cart_item_remove)
        var minus: TextView = view.findViewById(R.id.tvCartItemMinus)
        var add: TextView = view.findViewById(R.id.tvCartItemPlus)
        //var quantity: TextView = view.findViewById(R.id.tvQuantity)
    }

    interface OnCartItemClickListener{
        fun onCartItemClickListener(id: Int, price: Double)

        fun onRemoveClickListener(id: Int, price: Double)
    }

}