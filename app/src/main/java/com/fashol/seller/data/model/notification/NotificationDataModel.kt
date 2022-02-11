package com.fashol.seller.data.model.notification

data class NotificationDataModel(
    var error: Boolean, // false
    var message: String, // Notice successfully fetched!
    var result: List<Result>,
    var success: Boolean // true
) {
    data class Result(
        var created_at: Any, // null
        var date: String, // 9-02-2022
        var description: String, // This Application is now unstable stage..Nobody try to do anything for serious situation.Before doing some action please contact with Sajib (01797840513).
        var id: Int, // 1
        var role_id: Int, // 2
        var title: String, // Warning for everyone!
        var updated_at: Any // null
    )
}