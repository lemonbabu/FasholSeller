package com.fashol.seller.view.ui.fragment.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.databinding.FragmentOrderDetailsBinding

class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private lateinit var binding: FragmentOrderDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderDetailsBinding.bind(view)

    }

}