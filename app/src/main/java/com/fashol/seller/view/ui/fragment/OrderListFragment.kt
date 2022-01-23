package com.fashol.seller.view.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.fashol.seller.R
import com.fashol.seller.data.model.orderdata.OrderDataModel
import com.fashol.seller.databinding.FragmentOrderListBinding
import com.fashol.seller.view.adapter.OrderListAdapter

class OrderListFragment : Fragment(R.layout.fragment_order_list) {

    private lateinit var binding: FragmentOrderListBinding
    private lateinit var orderListAdapter: OrderListAdapter
    private var orderList: ArrayList<OrderDataModel> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderListBinding.bind(view)

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
        orderList.add(OrderDataModel("1", "F-WR2424", "Mr Hamidur", 2000.0F))
        orderList.add(OrderDataModel("2", "F-RK2221", "Kacha bazar", 1000.0F))
        orderList.add(OrderDataModel("3", "F-CR5367", "Mohonbag Bazar", 3422.0F))
        orderList.add(OrderDataModel("4", "F-AF2124", "Motiyar Rahman", 23232.0F))
        orderList.add(OrderDataModel("2", "F-RK2221", "Kacha bazar", 1000.0F))
        orderList.add(OrderDataModel("3", "F-CR5367", "Mohonbag Bazar", 3422.0F))
        orderList.add(OrderDataModel("4", "F-AF2124", "Motiyar Rahman", 23232.0F))
        orderList.add(OrderDataModel("1", "F-WR2424", "Mr Hamidur", 2000.0F))
        orderList.add(OrderDataModel("2", "F-RK2221", "Kacha bazar", 1000.0F))
        orderList.add(OrderDataModel("3", "F-CR5367", "Mohonbag Bazar", 3422.0F))
        orderList.add(OrderDataModel("4", "F-AF2124", "Motiyar Rahman", 23232.0F))

        binding.rvOrderList.layoutManager = GridLayoutManager(context,1)
        orderListAdapter = OrderListAdapter()
        orderListAdapter.submitList(orderList)
        binding.rvOrderList.adapter = orderListAdapter

    }

}