package com.fashol.seller.view.ui.fragment.notice

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.model.notification.NotificationDataModel
import com.fashol.seller.data.repository.local.NotificationData
import com.fashol.seller.databinding.FragmentNoticeListBinding
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.layout_notification_details.view.*
import kotlinx.coroutines.*
import retrofit2.awaitResponse


@DelicateCoroutinesApi
class NoticeListFragment : Fragment(R.layout.fragment_notice_list), NotificationAdapter.OnItemClickListener {

    private lateinit var binding: FragmentNoticeListBinding
    private lateinit var notificationAdapter: NotificationAdapter
    private val notApi: ApiInterfaces.NotificationInterface by lazy { RetrofitClient.getNotification() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoticeListBinding.bind(view)

        binding.rvNotice.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        notificationAdapter = NotificationAdapter(this)


        binding.notification.layoutNoticeDetails.btnClose.setOnClickListener {
            binding.notification.layoutNoticeDetails.visibility = View.GONE
        }


        if(!NotificationData.flag){
            binding.pbLoading.visibility = View.VISIBLE
            getAllNotification()
        }else{
            notificationAdapter.submitList(NotificationData.data)
            binding.rvNotice.adapter = notificationAdapter
        }

    }

    private fun getAllNotification(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = notApi.getNot("Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Notification Updated",  response.toString())
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            NotificationData.data = it
                            NotificationData.flag = true
                            notificationAdapter.submitList(NotificationData.data)
                            binding.rvNotice.adapter = notificationAdapter
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString()  , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error Notification ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Error occur Server not response!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    override fun onNotClickListener(data: NotificationDataModel.Result) {

        binding.notification.layoutNoticeDetails.visibility = View.VISIBLE
        binding.notification.layoutNoticeDetails.tvNoticeTitle.text = data.title
        binding.notification.layoutNoticeDetails.tvNoticeDetails.text = data.description
        binding.notification.layoutNoticeDetails.tvNoticeDate.text = data.date
    }
}