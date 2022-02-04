package com.fashol.seller.data.model.orderdata

data class CreateOrderRequestDataModel(
    var assigned_by: Int, // 2
    var customer_id: Int, // 1
    var note: String, // New order
    var order_lists: List<OrderLists>
) {
    data class OrderLists(
        var note: String,
        var product_id: Int, // 2
        var quantity: Double, // 20.50
        var status: String, // added
        var variant_id: Int // 88
    )
}