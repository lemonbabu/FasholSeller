package com.fashol.seller.data.model.orderdata

data class OrderConfirmationResponse(
    var error: Boolean, // false
    var message: String, // Order Successfully created!
    var result: Result,
    var success: Boolean // true
) {
    data class Result(
        var assigned_by: Int, // 2
        var customer: Customer,
        var customer_id: Int, // 1
        var id: Int, // 13
        var note: String, // New order
        var order_date: String, // 03-02-2022
        var order_list: List<Order>,
        var order_no: String, // OR-030222000008
        var ordered_by: Int, // 5
        var price_group_id: Int, // 1
        var status: String // pending
    ) {
        data class Customer(
            var added_by: Int, // 3
            var details: Details,
            var id: Int, // 1
            var name: String, // Rubel test
            var phone: String, // 1711877670
            var profile_pic: String, // /defaults/customer.jpeg
            var status: String, // active
            var store_address: String, // Vatara, Natun Bazar, Dhaka
            var store_name: String, // Rubel Store
            var unique_id: String, // FCM-000001
            var zone_id: Int // 10
        ) {
            data class Details(
                var address_line_1: String, // Panthapoth
                var address_line_2: Any, // null
                var customer_id: Int, // 1
                var dob: String, // 2021-09-04
                var geo_location: String, // ""
                var id: Int, // 1
                var nid_backpart: String, // customer_u_image_('unique_id').png
                var nid_frontpart: String, // customer_u_image_('unique_id').png
                var nid_number: String, // 123456789
                var store_image: String // customer_u_image_('unique_id').png
            )
        }

        data class Order(
            var id: Int, // 26
            var note: Any, // null
            var order_id: Int, // 13
            var product: Product,
            var product_id: Int, // 4
            var quantity: String, // 20.50
            var status: String, // created
            var variant: Variant,
            var variant_id: Int // 10
        ) {
            data class Product(
                var category: Category,
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
                data class Category(
                    var id: Int, // 1
                    var image: String, // /images/category/vagitable.png
                    var name: String // শাক-সবজি
                )

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