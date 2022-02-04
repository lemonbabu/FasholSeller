package com.fashol.seller.data.model.customerdata

data class CreateCustomerResponse(
    var error: Boolean, // false
    var message: String, // Customer Successfully Created!
    var result: Result,
    var success: Boolean // true
) {
    data class Result(
        var added_by: Int, // 5
        var details: Details,
        var id: Int, // 2762
        var name: String, // Customer-1
        var phone: String, // 01797840514
        var profile_pic: String, // /defaults/customer.jpeg
        var status: String, // active
        var store_address: String, // store-address
        var store_name: String, // store-1
        var unique_id: String, // CU-0-1573
        var zone: Zone,
        var zone_id: String // 1
    ) {
        data class Details(
            var address_line_1: String, // dhaka
            var address_line_2: String, // dhaka2
            var customer_id: Int, // 2762
            var dob: String, // 12-jan-2020
            var geo_location: String, // 232.23423.34523.5
            var id: Int, // 2762
            var nid_backpart: String, // /defaults/customer_nid_backpart.jpeg
            var nid_frontpart: String, // /defaults/customer_nid_frontpart.jpeg
            var nid_number: String, // 2342342323445
            var store_image: String // /defaults/customer_store_image.jpeg
        )

        data class Zone(
            var id: Int, // 1
            var name: String // Karwan Bajar (Office)
        )
    }
}