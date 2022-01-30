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
import com.fashol.seller.utilits.Utils
import com.squareup.picasso.Picasso

class CustomerAdapter(private var onItemClickListener: OnCustomerClickListener): RecyclerView.Adapter<CustomerAdapter.MyViewHolder>(){
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

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): CustomerAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_customer_single, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerAdapter.MyViewHolder, position: Int) {
        val customer = customerList[position]

        holder.name.text = customer.name
        holder.avatar.setImageResource(R.drawable.img_fruit)
        val url = Utils.baseUrl() +  customer.profile_pic

       // load image into view
        Picasso.get().load(url).into(holder.avatar)

        holder.itemView.setOnClickListener {
            onItemClickListener.onCustomerClickListener(customer.id, customer.name, customer.profile_pic)
        }
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView = view.findViewById(R.id.ivCustomerAvatar)
        var name: TextView = view.findViewById(R.id.tvCustomerName)
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