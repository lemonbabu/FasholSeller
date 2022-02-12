package com.fashol.seller.view.ui.fragment.order

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.repository.local.OrderDetailsData
import com.fashol.seller.data.repository.local.OrderListData
import com.fashol.seller.databinding.FragmentOrderListBinding
import com.fashol.seller.utilits.MainFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.adapter.OrderListAdapter
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class OrderListFragment : Fragment(R.layout.fragment_order_list), OrderListAdapter.OnOrderClickListener {

    private lateinit var binding: FragmentOrderListBinding
    private lateinit var fc: MainFragmentCommunicator
    private lateinit var orderListAdapter: OrderListAdapter
    private val orderListApi: ApiInterfaces.OrderListInterface by lazy { RetrofitClient.getOrderList() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderListBinding.bind(view)
        fc = activity as MainFragmentCommunicator

        //binding.rvOrderList.layoutManager = GridLayoutManager(context,1)
        orderListAdapter = OrderListAdapter(this)


        if(!OrderListData.flag){
            binding.pbLoading.visibility = View.VISIBLE
            loadOrderList()
        }else{
            orderListAdapter.submitList(OrderListData.data)
            binding.rvOrderList.adapter = orderListAdapter
        }

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
                            OrderListData.flag = true
                            OrderListData.data = it
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error Order List ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    override fun onOrderClickListener(id: Int) {
        OrderDetailsData.id = id
        fc.passData("OrderDetails")
    }

}