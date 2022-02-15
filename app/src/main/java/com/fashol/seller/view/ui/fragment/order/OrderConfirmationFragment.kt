package com.fashol.seller.view.ui.fragment.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.databinding.FragmentOrderConfirmationBinding
import com.fashol.seller.utilits.MainFragmentCommunicator


class OrderConfirmationFragment : Fragment(R.layout.fragment_order_confirmation) {

    private lateinit var binding: FragmentOrderConfirmationBinding
    private lateinit var fc: MainFragmentCommunicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderConfirmationBinding.bind(view)
        fc = activity as MainFragmentCommunicator

        binding.btnDashboard.setOnClickListener {
            fc.passData("Dashboard")
        }

        binding.btnOrders.setOnClickListener {
            fc.passData("OrderList")
        }
    }

}