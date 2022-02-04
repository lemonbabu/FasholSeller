package com.fashol.seller.data.model.orderdata

class CartItemDataModel(
    var id : String,
    var name: String,
    var avatar: String,
    var unitPrice: Double,
    var quantity: Int,
    var variant: String,
    var variantId: Int
) {
}