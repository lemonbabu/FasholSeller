package com.fashol.seller.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fashol.seller.R
import com.fashol.seller.databinding.FragmentDashboardBinding
import com.fashol.seller.utilits.FragmentCommunicator

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var fc: FragmentCommunicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        fc = activity as FragmentCommunicator


    }

}