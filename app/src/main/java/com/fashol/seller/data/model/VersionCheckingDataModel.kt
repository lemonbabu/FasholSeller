package com.fashol.seller.data.model

data class VersionCheckingDataModel(
    var error: Boolean, // false
    var message: String, // Seller Application Current Version
    var result: Result,
    var success: Boolean // true
) {
    data class Result(
        var created_at: String, // 2022-02-11 12:14:29
        var id: Int, // 1
        var updated_at: String, // 2022-02-11 12:14:25
        var version: String // 1.0.0
    )
}