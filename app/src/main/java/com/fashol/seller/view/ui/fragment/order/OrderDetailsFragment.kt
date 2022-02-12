package com.fashol.seller.view.ui.fragment.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.model.orderdata.OrderDetailsResponseDataModel
import com.fashol.seller.data.repository.local.OrderDetailsData
import com.fashol.seller.databinding.FragmentOrderDetailsBinding
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.adapter.OrderDetailsAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    val TAG = "Order Details"
    private lateinit var binding: FragmentOrderDetailsBinding
    private val orderDetailsApi: ApiInterfaces.OrderDetailsInterface by lazy { RetrofitClient.getOrderDetails() }
    private lateinit var itemListAdapter: OrderDetailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderDetailsBinding.bind(view)

        //binding.rvItemList.layoutManager = Lin(context,1)
        itemListAdapter = OrderDetailsAdapter()


        binding.pbLoading.visibility = View.VISIBLE
        loadDetails()

    }

    private fun loadDetails(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = orderDetailsApi.getOrderDetails(OrderDetailsData.id, "Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d(TAG,  response.toString())
                    if (response.body()?.success == true){
                       // Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            loadData(it)
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + OrderDetailsData.id.toString(), Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(TAG, e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Server Error Occur!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(result: OrderDetailsResponseDataModel.Result) {
        binding.apply {
            tvCustomerName.text = result.customer.name
            tvCustomerPhone.text = result.customer.phone
            tvCustomerAddress.text = result.customer.store_address
            tvOrderDate.text = result.order_date
            tvOrderId.text = "Order Id: " + result.order_no
            val url = Utils.baseUrl() +  result.customer.profile_pic
            // load image into view
            Picasso.get().load(url).placeholder(R.drawable.placeholder).into(ivCustomerAvatar)

            itemListAdapter.submitList(result.order_list)
            binding.rvItemList.adapter = itemListAdapter

        }
    }

}