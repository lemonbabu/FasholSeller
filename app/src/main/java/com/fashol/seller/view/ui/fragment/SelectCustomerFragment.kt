package com.fashol.seller.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.model.customerdata.CustomerDataModel
import com.fashol.seller.databinding.FragmentSelectCustomerBinding
import com.fashol.seller.view.adapter.CustomerAdapter

class SelectCustomerFragment : Fragment(R.layout.fragment_select_customer) {
   private lateinit var binding: FragmentSelectCustomerBinding
   private val customerData : ArrayList<CustomerDataModel> = ArrayList()
   private lateinit var customerAdapter: CustomerAdapter

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FragmentSelectCustomerBinding.bind(view)

      binding.rvTopCustomer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
      binding.rvCustomers.layoutManager = GridLayoutManager(context, 4)



      customerData.add(CustomerDataModel("1", "Md Rasel", "Md Rasel"))
      customerData.add(CustomerDataModel("1", "Md Motalib", "Motalib"))
      customerData.add(CustomerDataModel("1", "Md Rasel", "Ruhul Amin"))
      customerData.add(CustomerDataModel("1", "Md Rasel", "Mehedi Hasan"))
      customerData.add(CustomerDataModel("1", "Md Motalib", "Samira Begum"))
      customerData.add(CustomerDataModel("1", "Md Rasel", "Amjad Hosen"))
      customerData.add(CustomerDataModel("1", "Md Rasel", "Md Rasel"))
      customerData.add(CustomerDataModel("1", "Md Motalib", "Motalib"))
      customerData.add(CustomerDataModel("1", "Md Rasel", "Ruhul Amin"))

      customerAdapter = CustomerAdapter()
      customerAdapter.submitList(customerData)
      binding.rvTopCustomer.adapter= customerAdapter
      binding.rvCustomers.adapter = customerAdapter

   }

}