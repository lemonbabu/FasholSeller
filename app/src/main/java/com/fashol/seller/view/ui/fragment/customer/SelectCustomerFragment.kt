package com.fashol.seller.view.ui.fragment.customer

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.model.customerdata.CustomerDataModel
import com.fashol.seller.databinding.FragmentSelectCustomerBinding
import com.fashol.seller.utilits.MainFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.adapter.CustomerAdapter
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class SelectCustomerFragment : Fragment(R.layout.fragment_select_customer), CustomerAdapter.OnCustomerClickListener {
   private lateinit var binding: FragmentSelectCustomerBinding
   private val customerData : ArrayList<CustomerDataModel.Result> = ArrayList()
   private lateinit var customerAdapter: CustomerAdapter
   private lateinit var fc: MainFragmentCommunicator
   private val customerApi: ApiInterfaces.CustomerListInterface by lazy { RetrofitClient.getCustomerList() }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FragmentSelectCustomerBinding.bind(view)
      fc = activity as MainFragmentCommunicator

      binding.pbLoading.visibility = View.VISIBLE
      binding.rvTopCustomer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
      customerAdapter = CustomerAdapter(this)


      getAllCustomerList()

   }

   override fun onCustomerClickListener(id: Int, name: String, avatar: String) {

      val sharedPreferences = activity?.getSharedPreferences("Cart", Context.MODE_PRIVATE)
      val editor = sharedPreferences?.edit()
      editor?.apply{
         putBoolean("session", true)
         putInt("customerId", id)
         putString("customerName", name)
         putString("customerAvatar", avatar)
         putInt("cartItem", 0)
      }?.apply()

      fc.passData("ProductPage")

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
                     customerAdapter.submitList(it)
                     binding.rvTopCustomer.adapter= customerAdapter
                     binding.rvCustomers.adapter = customerAdapter
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


}