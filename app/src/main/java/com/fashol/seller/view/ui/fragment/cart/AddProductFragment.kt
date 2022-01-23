package com.fashol.seller.view.ui.fragment.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fashol.seller.R
import com.fashol.seller.databinding.FragmentAddProductBinding


class AddProductFragment : Fragment(R.layout.fragment_add_product) {

    private lateinit var binding: FragmentAddProductBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProductBinding.bind(view)

    }

}