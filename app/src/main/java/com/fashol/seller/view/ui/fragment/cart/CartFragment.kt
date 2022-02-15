package com.fashol.seller.view.ui.fragment.cart

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.data.repository.local.CartData
import com.fashol.seller.databinding.FragmentCartBinding
import com.fashol.seller.utilits.PopUpFragmentCommunicator
import com.fashol.seller.view.adapter.CartAdapter

class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.OnCartItemClickListener {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var fc: PopUpFragmentCommunicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)

        fc = activity as PopUpFragmentCommunicator

        binding.etOrderNote.addTextChangedListener {
            CartData.orderNote = it.toString().trim()
        }

        cartAdapter = CartAdapter(this)
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

    override fun onCartItemClickListener(id: Int, price: Double) {
        //
    }

    override fun onRemoveClickListener(id: Int, price: Double) {
        CartData.cartData.removeAt(id)
        CartData.totalAmount = CartData.totalAmount - price
        CartData.totalItem = CartData.totalItem - 1

        cartAdapter.submitList(CartData.cartData)
        binding.rvCart.adapter = cartAdapter
        fc.passPopUpData("reloadCart")
    }


}