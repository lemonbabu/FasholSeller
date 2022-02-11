package com.fashol.seller.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fashol.seller.R
import com.fashol.seller.data.model.notification.NotificationDataModel

class NotificationAdapter(private var onItemClickListener: NotificationAdapter.OnItemClickListener) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){
    private var itemList : ArrayList<NotificationDataModel.Result> = ArrayList()

    fun submitList(list: List<NotificationDataModel.Result>){
        itemList = list as ArrayList<NotificationDataModel.Result>
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.tvNoticeTitle)
        var date: TextView = view.findViewById(R.id.tvNoticeDate)
        var details: TextView = view.findViewById(R.id.tvNoticeDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_notification_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]

        holder.title.text = item.title
        holder.date.text = item.date
        holder.details.text = item.description

        holder.itemView.setOnClickListener {
            onItemClickListener.onNotClickListener(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface OnItemClickListener {
        fun onNotClickListener(data: NotificationDataModel.Result)
    }

}