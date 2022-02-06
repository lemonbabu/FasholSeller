package com.fashol.seller.view.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.model.orderdata.OrderDataModel
import com.fashol.seller.databinding.FragmentDashboardBinding
import com.fashol.seller.utilits.MainFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.adapter.OrderListAdapter
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class DashboardFragment : Fragment(R.layout.fragment_dashboard), OrderListAdapter.OnOrderClickListener {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var fc: MainFragmentCommunicator
    private lateinit var orderListAdapter: OrderListAdapter
    private val orderListApi: ApiInterfaces.OrderListInterface by lazy { RetrofitClient.getOrderList() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        fc = activity as MainFragmentCommunicator

        binding.rvOrderList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        orderListAdapter = OrderListAdapter(this)

        binding.tvAllOrder.setOnClickListener {
            fc.passData("OrderList")
        }


        binding.txtSeeMore.setOnClickListener {
            fc.passData("NoticeList")
        }
        loadOrderList()

    }

    private fun loadOrderList(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = orderListApi.getOrderList("Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Order List: ",  response.toString())
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            orderListAdapter.submitList(it)
                            binding.rvOrderList.adapter = orderListAdapter
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error Order List ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Server not response some error occur!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    override fun onOrderClickListener() {
        fc.passData("OrderDetails")
    }

}