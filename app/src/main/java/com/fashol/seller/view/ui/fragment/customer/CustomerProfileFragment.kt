package com.fashol.seller.view.ui.fragment.customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.data.repository.local.CustomerListData
import com.fashol.seller.databinding.FragmentCustomerProfileBinding
import com.fashol.seller.utilits.Utils
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CustomerProfileFragment : Fragment(R.layout.fragment_customer_profile) {

    private lateinit var binding: FragmentCustomerProfileBinding
    private var basicInfoFlag = false
    private var locationInfoFlag = false
    private var dueInfoFlag = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomerProfileBinding.bind(view)


        if(CustomerListData.index == -1){
            Snackbar.make(view, "Error occur no user found", Snackbar.LENGTH_SHORT).show()
        } else{
            loadData(CustomerListData.index)
        }

        binding.ibBasicInfo.setOnClickListener {
            if(basicInfoFlag){
                basicInfoFlag = false
                binding.cvBasicInfo.visibility = View.GONE
                binding.ibBasicInfo.setImageResource(R.drawable.ic_material_keyboard_arrow_left)
            }else{
                basicInfoFlag = true
                binding.cvBasicInfo.visibility = View.VISIBLE
                binding.ibBasicInfo.setImageResource(R.drawable.ic_feather_chevron_down)
            }
        }

        binding.ibLocationDetails.setOnClickListener {
            if(locationInfoFlag){
                locationInfoFlag = false
                binding.cvLocationInfo.visibility = View.GONE
                binding.ibLocationDetails.setImageResource(R.drawable.ic_material_keyboard_arrow_left)
            }else{
                locationInfoFlag = true
                binding.cvLocationInfo.visibility = View.VISIBLE
                binding.ibLocationDetails.setImageResource(R.drawable.ic_feather_chevron_down)
            }
        }

        binding.ibDueManagement.setOnClickListener {
            if(dueInfoFlag){
                dueInfoFlag = false
                binding.cvDueInfo.visibility = View.GONE
                binding.ibDueManagement.setImageResource(R.drawable.ic_material_keyboard_arrow_left)
            }else{
                dueInfoFlag = true
                binding.cvDueInfo.visibility = View.VISIBLE
                binding.ibDueManagement.setImageResource(R.drawable.ic_feather_chevron_down)
            }
        }

    }


    private fun loadData(index: Int){
        binding.tvUserName.text = CustomerListData.data[index].name
        binding.tvCustomerPhone.text = CustomerListData.data[index].phone
        binding.tvShopAddress.text = CustomerListData.data[index].store_address

        binding.tv1Amount.text = "0"
        binding.tv1Value.text = "This Month"
        binding.tv2Amount.text = "0"
        binding.tv2Value.text = "Due"
        binding.tv3Amount.text = "0"
        binding.tv3Value.text = "Total"

        binding.tvFullName.text = CustomerListData.data[index].name
        binding.tvPhone.text = CustomerListData.data[index].phone
        binding.tvEmail.text = ""

        binding.tvAddress.text = CustomerListData.data[index].status

        val url = Utils.baseUrl() +  CustomerListData.data[index].profile_pic
        // load image into view
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(binding.imageView)
    }

}