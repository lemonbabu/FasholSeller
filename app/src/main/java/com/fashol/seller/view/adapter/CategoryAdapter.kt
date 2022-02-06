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
    private var categoryList: ArrayList<CategoryDataModel.Result> = ArrayList()

    fun submitList(list: List<CategoryDataModel.Result>){
        val oldList = categoryList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            BookDiffCallBack(
                oldList,
                list
            )
        )
        categoryList = list as ArrayList<CategoryDataModel.Result>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_customer_single, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.MyViewHolder, position: Int) {
        val category = categoryList[position]

        holder.name.text = category.name
       // holder.avatar.setImageResource(R.drawable.img_fruit)

        val url = Utils.baseUrl() + category.image
        // load image into view
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(holder.avatar)

        holder.itemView.setOnClickListener {
            onItemClickListener.onCategoryClickListener(category.id)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView = view.findViewById(R.id.ivCustomerAvatar)
        var name: TextView = view.findViewById(R.id.tvCustomerName)
    }

    class BookDiffCallBack(
        private var oldCategoryList: List<CategoryDataModel.Result>,
        private var newCategoryList: List<CategoryDataModel.Result>
    ): DiffUtil.Callback(){

        override fun getOldListSize(): Int {
            return oldCategoryList.size
        }

        override fun getNewListSize(): Int {
            return newCategoryList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldCategoryList[oldItemPosition].id == newCategoryList[newItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCategoryList[oldItemPosition] == newCategoryList[newItemPosition]
        }

    }

    interface OnCategoryClickListener{
        fun onCategoryClickListener(id: Int)
    }
}