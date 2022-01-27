package com.fashol.seller.view.ui.fragment.customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fashol.seller.R
import com.fashol.seller.databinding.FragmentAddNewCustomerBinding

class AddNewCustomerFragment : Fragment(R.layout.fragment_add_new_customer) {

    private lateinit var binding: FragmentAddNewCustomerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewCustomerBinding.bind(view)

    }

}