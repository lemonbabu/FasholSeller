package com.fashol.seller.view.ui.fragment.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fashol.seller.R
import com.fashol.seller.databinding.FragmentCustomerProfileBinding

class CustomerProfileFragment : Fragment(R.layout.fragment_customer_profile) {

    private lateinit var binding: FragmentCustomerProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomerProfileBinding.bind(view)

    }

}