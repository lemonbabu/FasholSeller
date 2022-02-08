package com.fashol.seller.view.ui.fragment.customer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.data.api.ApiInterfaces
import com.fashol.seller.data.api.RetrofitClient
import com.fashol.seller.data.repository.local.CustomerListData
import com.fashol.seller.databinding.FragmentAddNewCustomerBinding
import com.fashol.seller.utilits.MainFragmentCommunicator
import com.fashol.seller.utilits.Utils
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class AddNewCustomerFragment : Fragment(R.layout.fragment_add_new_customer) {

    private lateinit var binding: FragmentAddNewCustomerBinding
    private lateinit var fc: MainFragmentCommunicator
    private val createCustomerApi: ApiInterfaces.CreateCustomerInterface by lazy { RetrofitClient.createNewCustomer() }
    private var zone = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewCustomerBinding.bind(view)
        fc = activity as MainFragmentCommunicator

        binding.btnCancel.setOnClickListener {
            fc.passData("CustomerList")
        }

        binding.btnSubmit.setOnClickListener {
            val cName = binding.tvCustomerName.editText?.text.toString()
            if(cName.isEmpty()){
                binding.tvCustomerName.error = "Need to fill up!"
                binding.tvCustomerName.requestFocus()
                return@setOnClickListener
            }

            val cPhone = binding.tvCustomerPhone.editText?.text.toString()
            if(cPhone.isEmpty()){
                binding.tvCustomerPhone.error = "Need to fill up!"
                binding.tvCustomerPhone.requestFocus()
                return@setOnClickListener
            }

            if(cPhone.length < 11){
                binding.tvCustomerPhone.error = "Minimum 11 digit!"
                binding.tvCustomerPhone.requestFocus()
                return@setOnClickListener
            }

            val nId = binding.tvCustomerNID.editText?.text.toString()
            if(nId.isEmpty()){
                binding.tvCustomerNID.error = "Need to fill up!"
                binding.tvCustomerNID.requestFocus()
                return@setOnClickListener
            }

            if(nId.length < 13){
                binding.tvCustomerNID.error = "Minimum 13 digit!"
                binding.tvCustomerNID.requestFocus()
                return@setOnClickListener
            }

            val dob = binding.tvDateOfBirth.editText?.text.toString()
            if(dob.isEmpty()){
                binding.tvDateOfBirth.error = "Need to fill up!"
                binding.tvDateOfBirth.requestFocus()
                return@setOnClickListener
            }

            val storeName = binding.tvStoreName.editText?.text.toString()
            if(storeName.isEmpty()){
                binding.tvStoreName.error = "Need to fill up!"
                binding.tvStoreName.requestFocus()
                return@setOnClickListener
            }

            val address = binding.tvCustomerAddress.editText?.text.toString()
            if(address.isEmpty()){
                binding.tvCustomerAddress.error = "Need to fill up!"
                binding.tvCustomerAddress.requestFocus()
                return@setOnClickListener
            }


            binding.pbLoading.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = createCustomerApi.createNewCustomer(name = cName, phone = cPhone, nidNumber =  nId, dob = dob, zoneId =  1, storeName =  storeName, storeAddress =  address, auth =  "Bearer ${Utils.token()}").awaitResponse()
                    withContext(Dispatchers.Main){
                        if (response.body()?.success == true){
                            //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                            response.body()?.result?.let {
                                Log.d("Crate new customer ",  it.toString())
                                Toast.makeText(context, "$cName create successfully", Toast.LENGTH_SHORT).show()
                                fc.passData("CustomerList")
                                CustomerListData.flag = false // This is for loading customer API
                            }
                        }else{
                            Toast.makeText(context, response.body()?.message.toString() + " " + response.body().toString() , Toast.LENGTH_SHORT).show()
                            Log.d("Create Customer = ", response.body().toString())
                        }
                        binding.pbLoading.visibility = View.GONE
                    }
                }catch (e: Exception) {
                    Log.d(" Error Create Customer ", e.toString())
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context,"Error occur Server not response!!", Toast.LENGTH_SHORT).show()
                        binding.pbLoading.visibility = View.GONE
                    }
                }
            }

        }

    }

}