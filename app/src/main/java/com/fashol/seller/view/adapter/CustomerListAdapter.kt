package com.fashol.seller.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.customerdata.CustomerDataModel
import com.fashol.seller.data.repository.local.CartData
import com.fashol.seller.utilits.Utils
import com.squareup.picasso.Picasso

class CustomerListAdapter(private var onItemClickListener: OnCustomerClickListener): RecyclerView.Adapter<CustomerListAdapter.MyViewHolder>(){
    private var customerList: ArrayList<CustomerDataModel.Result> = ArrayList()

    fun submitList(list: List<CustomerDataModel.Result>){
        val oldList = customerList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            BookDiffCallBack(
                oldList,
                list
            )
        )
        customerList = list as ArrayList<CustomerDataModel.Result>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerListAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_customer_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerListAdapter.MyViewHolder, position: Int) {
        val customer = customerList[position]

        holder.name.text = customer.name
        holder.phone.text = customer.phone
        holder.shop.text = customer.store_name
        val url = Utils.baseUrl() +  customer.profile_pic

        // load image into view
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(holder.avatar)

        holder.itemView.setOnClickListener {
            CartData.customerId = customer.id.toString()
            CartData.customerName = customer.name
            onItemClickListener.onCustomerClickListener(customer.id, customer.name, customer.profile_pic)
        }
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView = view.findViewById(R.id.ivCustomerAvatar)
        var name: TextView = view.findViewById(R.id.tvCustomerName)
        var phone: TextView = view.findViewById(R.id.tvCustomerPhone)
        var shop: TextView = view.findViewById(R.id.tvCustomerShop)
    }

    class BookDiffCallBack(
        private var oldCustomerList: List<CustomerDataModel.Result>,
        private var newCustomerList: List<CustomerDataModel.Result>
    ): DiffUtil.Callback(){

        override fun getOldListSize(): Int {
            return oldCustomerList.size
        }

        override fun getNewListSize(): Int {
            return newCustomerList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldCustomerList[oldItemPosition].id == newCustomerList[newItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCustomerList[oldItemPosition] == newCustomerList[newItemPosition]
        }

    }

    interface OnCustomerClickListener{
        fun onCustomerClickListener(id: Int, name: String, avatar: String)
    }
}