package com.fashol.seller.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.model.orderdata.OrderDataModel
import com.fashol.seller.databinding.FragmentDashboardBinding
import com.fashol.seller.utilits.MainFragmentCommunicator
import com.fashol.seller.view.adapter.OrderListAdapter

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var fc: MainFragmentCommunicator
    private lateinit var orderListAdapter: OrderListAdapter
    private var orderList: ArrayList<OrderDataModel> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        fc = activity as MainFragmentCommunicator

        binding.tvAllOrder.setOnClickListener {
            fc.passData("OrderList")
        }

        orderList.add(OrderDataModel("1", "F-WR2424", "Mr Hamidur", 2000.0F))
        orderList.add(OrderDataModel("2", "F-RK2221", "Kacha bazar", 1000.0F))
        orderList.add(OrderDataModel("3", "F-CR5367", "Mohonbag Bazar", 3422.0F))
        orderList.add(OrderDataModel("4", "F-AF2124", "Motiyar Rahman", 23232.0F))
        orderList.add(OrderDataModel("1", "F-WR2424", "Mr Hamidur", 2000.0F))
        orderList.add(OrderDataModel("2", "F-RK2221", "Kacha bazar", 1000.0F))
        orderList.add(OrderDataModel("3", "F-CR5367", "Mohonbag Bazar", 3422.0F))
        orderList.add(OrderDataModel("4", "F-AF2124", "Motiyar Rahman", 23232.0F))
        orderList.add(OrderDataModel("1", "F-WR2424", "Mr Hamidur", 2000.0F))
        orderList.add(OrderDataModel("2", "F-RK2221", "Kacha bazar", 1000.0F))
        orderList.add(OrderDataModel("3", "F-CR5367", "Mohonbag Bazar", 3422.0F))
        orderList.add(OrderDataModel("4", "F-AF2124", "Motiyar Rahman", 23232.0F))

        binding.rvOrderList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        orderListAdapter = OrderListAdapter()
        orderListAdapter.submitList(orderList)
        binding.rvOrderList.adapter = orderListAdapter


    }

}