package com.fashol.seller.data.repository.local

import com.fashol.seller.data.model.orderdata.OrderListDataModel

object OrderListData {
    var flag = false
    lateinit var data : List<OrderListDataModel.Result>
}