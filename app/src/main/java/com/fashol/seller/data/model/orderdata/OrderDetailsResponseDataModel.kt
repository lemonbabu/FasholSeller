package com.fashol.seller.data.model.orderdata

data class OrderDetailsResponseDataModel(
    var error: Boolean, // false
    var message: String, // Order Successfully fetched!
    var result: Result,
    var success: Boolean // true
) {
    data class Result(
        var assigned_by: Int, // 1
        var customer: Customer,
        var customer_id: Int, // 1
        var id: Int, // 1
        var note: String, // New order
        var order_date: String, // -000001-11-29T17:58:20.000000Z
        var order_list: List<Order>,
        var order_no: String, // OR-300122000001
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
            var id: Int, // 1
            var note: Any, // null
            var order_id: Int, // 1
            var product: Product,
            var product_id: Int, // 2
            var quantity: String, // 20.50
            var status: String, // created
            var variant: Variant,
            var variant_id: Int // 88
        ) {
            data class Product(
                var category: Category,
                var category_id: Int, // 4
                var description: String, // কাঁঠাল
                var id: Int, // 2
                var image_path: String, // /images/product/product_F_PR395216_10_21_10_10_36.jpg
                var name: String, // কাঁঠাল
                var packaging_unit: String, // 1
                var sku: String, // FSKU-0002
                var tags: List<String>,
                var unit: Unit,
                var unit_id: Int // 2
            ) {
                data class Category(
                    var id: Int, // 4
                    var image: String, // /images/category/fruits.png
                    var name: String // ফল
                )

                data class Unit(
                    var id: Int, // 2
                    var name: String // Pcs
                )
            }

            data class Variant(
                var id: Int, // 88
                var name: String // মিস্টি ক্যারেট ex
            )
        }
    }
}