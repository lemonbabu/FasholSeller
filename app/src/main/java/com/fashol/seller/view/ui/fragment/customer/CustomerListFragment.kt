package com.fashol.seller.view.ui.fragment.customer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.databinding.FragmentCustomerListBinding
import com.fashol.seller.utilits.MainFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.fashol.seller.view.adapter.CustomerAdapter
import com.fashol.seller.view.adapter.CustomerListAdapter
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class CustomerListFragment : Fragment(R.layout.fragment_customer_list), CustomerListAdapter.OnCustomerClickListener {

    private lateinit var binding: FragmentCustomerListBinding
    private lateinit var customerAdapter: CustomerListAdapter
    private lateinit var fc: MainFragmentCommunicator
    private val customerApi: ApiInterfaces.CustomerListInterface by lazy { RetrofitClient.getCustomerList() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomerListBinding.bind(view)
        fc = activity as MainFragmentCommunicator

        binding.pbLoading.visibility = View.VISIBLE
        binding.rvCustomerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        customerAdapter = CustomerListAdapter(this)

        getAllCustomerList()

    }

    override fun onCustomerClickListener(id: Int, name: String, avatar: String) {
        fc.passData("CustomerProfile")
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
                            binding.rvCustomerList.adapter= customerAdapter
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