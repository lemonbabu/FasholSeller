package com.fashol.seller.data.model.profile

data class LogoutResponseDataModel(
    var error: Boolean, // false
    var message: String, // Logout Successful!
    var result: List<Any>,
    var success: Boolean // true
)