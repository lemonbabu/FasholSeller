package com.fashol.seller.data.model.location

data class LocationDataModel(
    var error: Boolean, // false
    var message: String, // Successfully Stored locations!
    var result: Result,
    var success: Boolean // true
) {
    data class Result(
        var created_at: String, // 2022-03-06T13:47:18.000000Z
        var id: Int, // 5
        var latitude: String, // 1.333
        var location_date_id: Int, // 3
        var longitude: String, // 22.002
        var updated_at: String // 2022-03-06T13:47:18.000000Z
    )
}