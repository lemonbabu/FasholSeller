package com.fashol.seller.view.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.repository.local.CustomerListData
import com.fashol.seller.data.repository.local.OrderListData
import com.fashol.seller.data.repository.local.SellerProfile
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
    private val sellerProfileApi: ApiInterfaces.SellerProfileInterface by lazy { RetrofitClient.getSellerProfile() }
    private val customerApi: ApiInterfaces.CustomerListInterface by lazy { RetrofitClient.getCustomerList() }

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

        if(!OrderListData.flag){
            binding.pbLoading.visibility = View.VISIBLE
            loadOrderList()
        }else{
            orderListAdapter.submitList(OrderListData.data)
            binding.rvOrderList.adapter = orderListAdapter
        }

        if(!SellerProfile.flag){
            sellerProfile()
        } else{
            loadDashboardData()
        }

        if(!CustomerListData.flag){
            getAllCustomerList()
        }

    }

    // Order List api calling for dashboard
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
                    Toast.makeText(context,"Server not response some error occur!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    // API calling for Seller Profile and dashboard data and save in the object
    private fun sellerProfile(){
        Log.d("Profile= ", "call Api")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = sellerProfileApi.getSellerProfile("Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            Log.d("Order Confirmed: ",  it.toString())
                            SellerProfile.flag = true
                            SellerProfile.data = it
                            fc.passData("loadProfile")
                            loadDashboardData()
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error to Order ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Error occur Server not response!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    //This function for dashboard data
    private fun loadDashboardData(){
        binding.tv1.text = SellerProfile.data.dashboard[0].name
        binding.tv1Number.text = SellerProfile.data.dashboard[0].value.toString()
        binding.tv2.text = SellerProfile.data.dashboard[1].name
        binding.tv2Number.text = SellerProfile.data.dashboard[1].value.toString()
        binding.tv3.text = SellerProfile.data.dashboard[2].name
        binding.tv3Number.text = SellerProfile.data.dashboard[2].value.toString()
        binding.tv4.text = SellerProfile.data.dashboard[3].name
        binding.tv4Number.text = SellerProfile.data.dashboard[3].value.toString()
    }

    private fun getAllCustomerList(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = customerApi.getCustomerList("Bearer ${Utils.token()}").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Customer List Updated",  response.toString())
                    if (response.body()?.success == true){
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        response.body()?.result?.let {
                            CustomerListData.data = it
                            CustomerListData.flag = true
                        }
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                    binding.pbLoading.visibility = View.GONE
                }
            }catch (e: Exception) {
                Log.d(" Error Customer ", e.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Error occur Server not response!!", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    //Item click listener
    override fun onOrderClickListener() {
        fc.passData("OrderDetails")
    }

}