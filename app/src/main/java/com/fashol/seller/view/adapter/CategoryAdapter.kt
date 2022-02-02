package com.fashol.seller.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.productdata.CategoryDataModel
import com.fashol.seller.utilits.Utils
import com.squareup.picasso.Picasso

class CategoryAdapter(private var onItemClickListener: OnCategoryClickListener): RecyclerView.Adapter<CategoryAdapter.MyViewHolder>(){
    private var customerList: ArrayList<CategoryDataModel.Result> = ArrayList()

    fun submitList(list: List<CategoryDataModel.Result>){
        val oldList = customerList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            BookDiffCallBack(
                oldList,
                list
            )
        )
        customerList = list as ArrayList<CategoryDataModel.Result>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_customer_single, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.MyViewHolder, position: Int) {
        val customer = customerList[position]

        holder.name.text = customer.name
       // holder.avatar.setImageResource(R.drawable.img_fruit)

        val url = Utils.baseUrl() + customer.image
        // load image into view
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(holder.avatar)

        holder.itemView.setOnClickListener {
            onItemClickListener.onCategoryClickListener(customer.id, customer.name, customer.image)
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
        private var oldCustomerList: List<CategoryDataModel.Result>,
        private var newCustomerList: List<CategoryDataModel.Result>
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

    interface OnCategoryClickListener{
        fun onCategoryClickListener(id: Int, name: String, avatar: String)
    }
}