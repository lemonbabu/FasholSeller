package com.fashol.seller.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.productdata.ProductDataModel

class ProductAdapter(private var onItemClickListener: OnProductClickListener): RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private var productList: ArrayList<ProductDataModel> = ArrayList()

    fun submitList(list: List<ProductDataModel>) {
        val oldList = productList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            BookDiffCallBack(
                oldList,
                list
            )
        )
        productList = list as ArrayList<ProductDataModel>
        diffResult.dispatchUpdatesTo(this)
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView = view.findViewById(R.id.ivProductImage)
        var name: TextView = view.findViewById(R.id.tvProductName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_product_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductAdapter.MyViewHolder, position: Int) {
        val product = productList[position]

        holder.name.text = product.name
        holder.avatar.setImageResource(R.drawable.img_fruit)

        //val avatar = customer.avatar
        //load image into view
        //Picasso.get().load(avatar).fit().into(holder.avatar)

        holder.itemView.setOnClickListener {
            onItemClickListener.onProductClickListener(product.id, product.name, product.avatar)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class BookDiffCallBack(
        private var oldProductList: List<ProductDataModel>,
        private var newProductList: List<ProductDataModel>
    ): DiffUtil.Callback(){

        override fun getOldListSize(): Int {
            return oldProductList.size
        }

        override fun getNewListSize(): Int {
            return newProductList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldProductList[oldItemPosition].id == newProductList[newItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldProductList[oldItemPosition].equals(newProductList[newItemPosition])
        }
    }

    interface OnProductClickListener{
        fun onProductClickListener(id: String, name: String, avatar: String)
    }

}