data class test(
    var error: Boolean, // false
    var message: String, // Order Successfully fetched!
    var result: List<Result>,
    var success: Boolean // true
) {
    data class Result(
        var assigned_by: Int, // 2
        var customer: Customer,
        var customer_id: Int, // 1
        var id: Int, // 2
        var note: String, // New order
        var order_date: String, // 29-01-2022
        var order_list: List<Order>,
        var order_no: String, // OR-290122000001
        var ordered_by: Int, // 1
        var price_group_id: Int, // 1
        var status: String // pending
    ) {
        data class Customer(
            var added_by: Int, // 1
            var details: Details,
            var id: Int, // 1
            var name: String, // Customer-1
            var phone: String, // 01797840510
            var price_group_id: Int, // 1
            var profile_pic: String, // /defaults/customer.jpeg
            var status: String, // active
            var store_address: String, // store-address
            var store_name: String, // store-1
            var unique_id: String, // CU-000001
            var zone_id: Int // 1
        ) {
            data class Details(
                var address_line_1: String, // dhaka
                var address_line_2: String, // dhaka2
                var customer_id: Int, // 1
                var dob: String, // 12-jan-2020
                var geo_location: String, // 232.23423.34523.5
                var id: Int, // 1
                var nid_backpart: String, // /defaults/customer_nid_backpart.jpeg
                var nid_frontpart: String, // /defaults/customer_nid_frontpart.jpeg
                var nid_number: String, // 2342342323445
                var store_image: String // /defaults/customer_store_image.jpeg
            )
        }

        data class Order(
            var id: Int, // 1
            var note: Any, // null
            var order_id: Int, // 4
            var product: Product,
            var product_id: Int, // 2
            var quantity: String, // 20.50
            var status: String, // created
            var variant: Variant,
            var variant_id: Int // 88
        ) {
            data class Product(
                var category: Category,
                var category_id: Int, // 1
                var description: String, // Quis ad aliquid beatae voluptate dicta incidunt officia. Soluta at consectetur et nobis quia. Iste in quis quasi reprehenderit voluptas et. Excepturi dolores velit sint ut similique voluptate. Autem excepturi qui beatae aspernatur.
                var id: Int, // 2
                var image_path: String, // /defaults/tomato.jpg
                var name: String, // tenetur
                var packaging_unit: String, // 90.97
                var sku: String, // SKU-1215
                var tags: List<String>,
                var unit: Unit,
                var unit_id: Int // 1
            ) {
                data class Category(
                    var id: Int, // 1
                    var image: String, // /defaults/vagitable.jpeg
                    var name: String // Vagitables
                )

                data class Unit(
                    var id: Int, // 1
                    var name: String // kg
                )
            }

            data class Variant(
                var id: Int, // 88
                var name: String // sit
            )
        }
    }
}