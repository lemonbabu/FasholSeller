package com.fashol.seller.data.repository.local

import com.fashol.seller.data.model.productdata.CartItemDataModel

object CartData {
    var customerId: String = ""
    var customerName: String = ""
    var cartData : ArrayList<CartItemDataModel> = arrayListOf()
    var totalItem = 0
    var totalAmount : Double = 0.00
}