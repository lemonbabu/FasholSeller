package com.fashol.seller.view.ui.fragment.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fashol.seller.R
import com.fashol.seller.databinding.FragmentNoticeListBinding


class NoticeListFragment : Fragment(R.layout.fragment_notice_list) {

    private lateinit var binding: FragmentNoticeListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoticeListBinding.bind(view)

    }
}