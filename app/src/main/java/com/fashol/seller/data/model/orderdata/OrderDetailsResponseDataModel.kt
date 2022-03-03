package com.fashol.seller.data.model.orderdata

data class OrderDetailsResponseDataModel(
    var error: Boolean, // false
    var message: String, // Order Successfully fetched!
    var result: Result,
    var success: Boolean // true
) {
    data class Result(
        var assigned_by: Int, // 2
        var customer: Customer,
        var customer_id: Int, // 8
        var id: Int, // 9
        var note: String, // first order
        var order_date: String, // 0000-00-00
        var order_list: List<Order>,
        var order_no: String, // OR-030222000007
        var ordered_by: Int, // 5
        var price_group_id: Int, // 1
        var status: String // pending
    ) {
        data class Customer(
            var added_by: Int, // 72
            var details: Details,
            var id: Int, // 8
            var name: String, // 6 Rubel Store Basundhara
            var phone: String, // 1852802589
            var profile_pic: String, // /defaults/customer.jpeg
            var status: String, // active
            var store_address: String, // GhatFar Bashundhara
            var store_name: String, // 6 Rubel Store Basundhara
            var unique_id: String, // FCM-000008
            var zone_id: Int // 12
        ) {
            data class Details(
                var address_line_1: String, // GhatFar Bashundhara
                var address_line_2: String, // None
                var customer_id: Int, // 8
                var dob: String, // 2021-09-06
                var geo_location: String, // ""
                var id: Int, // 8
                var nid_backpart: String, // customer_u_image_12_Sep_12_33.png
                var nid_frontpart: String, // customer_u_image_12_Sep_12_33.png
                var nid_number: String, // 123456789
                var store_image: String // customer_u_image_12_Sep_12_33.png
            )
        }

        data class Order(
            var id: Int, // 17
            var note: Any, // null
            var order_id: Int, // 9
            var per_price: Int, // 0
            var product: Product,
            var product_id: Int, // 4
            var quantity: String, // 23.00
            var status: String, // created
            var total_price: Int, // 0
            var variant: Variant,
            var variant_id: Int // 10
        ) {
            data class Product(
                var category_id: Int, // 1
                var description: String, // বরবটি
                var id: Int, // 4
                var image_path: String, // /images/product/product_21_Sep_21_08.jpg
                var name: String, // বরবটি
                var packaging_unit: String, // 5
                var sku: String, // FSKU-0004
                var tags: List<String>,
                var unit: Unit,
                var unit_id: Int // 1
            ) {
                data class Unit(
                    var id: Int, // 1
                    var name: String // Kg
                )
            }

            data class Variant(
                var id: Int, // 10
                var name: String // সাদা ছোট
            )
        }
    }
}