package com.fashol.seller.view.ui.fragment.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fashol.seller.R
import com.fashol.seller.data.model.productdata.CartItemDataModel
import com.fashol.seller.data.repository.local.CartData
import com.fashol.seller.databinding.FragmentCartBinding
import com.fashol.seller.view.adapter.CartAdapter

class CartFragment : Fragment(R.layout.fragment_cart) {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)

        cartAdapter = CartAdapter()
        if(CartData.totalItem == 0){
            binding.tvEmptyCart.visibility = View.VISIBLE
            binding.rvCart.visibility = View.GONE
        } else{
            binding.tvEmptyCart.visibility = View.GONE
            binding.rvCart.visibility = View.VISIBLE
        }

//        cartData.add(CartItemDataModel("1", "Tomato", ""))
//        cartData.add(CartItemDataModel("1", "Potato", ""))
//        cartData.add(CartItemDataModel("1", "Begun", ""))
//        cartData.add(CartItemDataModel("1", "Sosa", ""))

        cartAdapter.submitList(CartData.cartData)
        binding.rvCart.adapter = cartAdapter

    }

}