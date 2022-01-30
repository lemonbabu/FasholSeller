package com.fashol.seller.data.model.customerdata

data class CustomerDataModel(
    var error: Boolean, // false
    var message: String, // Customer Successfully Fetched!
    var result: List<Result>,
    var success: Boolean // true
) {
    data class Result(
        var added_by: Int, // 1
        var details: Details,
        var id: Int, // 2
        var name: String, // customer-demo
        var phone: String, // 01797840515
        var price_group: PriceGroup,
        var price_group_id: Int, // 1
        var profile_pic: String, // /defaults/customer.jpeg
        var status: String, // active
        var store_address: String, // karwan bajar
        var store_name: String, // Vai vai store
        var unique_id: String, // CU-000001
        var zone: Zone,
        var zone_id: Int // 1
    ) {
        data class Details(
            var address_line_1: String, // Karwan bajar-1
            var address_line_2: String, // Karwan bajar-2
            var customer_id: Int, // 2
            var dob: String, // 12-jun-2022
            var geo_location: String, // 23.234.23.42.34.2
            var id: Int, // 2
            var nid_backpart: String, // /defaults/customer_nid_frontpart.jpeg
            var nid_frontpart: String, // /defaults/customer_nid_frontpart.jpeg
            var nid_number: String, // 0123456789012
            var store_image: String // /defaults/customer_store_image.jpeg
        )

        data class PriceGroup(
            var id: Int, // 1
            var name: String // Regular Price
        )

        data class Zone(
            var id: Int, // 1
            var name: String // Karwan Bazar
        )
    }
}