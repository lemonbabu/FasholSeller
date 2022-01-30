package com.fashol.seller.view.ui.fragment.cart

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fashol.seller.R
import com.fashol.seller.databinding.FragmentProductDetailsBinding
import com.fashol.seller.utilits.PopUpFragmentCommunicator
import com.fashol.seller.utilits.Utils
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var fcpopup: PopUpFragmentCommunicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)
        fcpopup = activity as PopUpFragmentCommunicator

        loadData()

        binding.tvClose.setOnClickListener {
            fcpopup.passPopUpData("close")
        }

        binding.btnAddCart.setOnClickListener {
            addItemInCart()
            fcpopup.passPopUpData("close")
        }


    }

    private fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val remember = sharedPreferences?.getBoolean("session", false)
        if(remember == true){
            binding.tvProductName.text = sharedPreferences.getString("productName", "....").toString()
            val url = Utils.baseUrl() + sharedPreferences.getString("productAvatar", "").toString()
            //load image into view
            Picasso.get().load(url).placeholder(R.drawable.img_fruit).into(binding.ivProductImage)
        }
    }

    private fun addItemInCart(){
        var item = 0
        val sharedPreferences = activity?.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val remember = sharedPreferences?.getBoolean("session", false)
        if(remember==true){
            item = sharedPreferences.getInt("cartItem", 0)
            item += 1
        }

        val editor = sharedPreferences?.edit()
        editor?.apply{
            putBoolean("session", true)
            putInt("cartItem", item)
        }?.apply()
        Toast.makeText(requireContext(), "Add new Product in Cart", Toast.LENGTH_SHORT).show()
    }

}