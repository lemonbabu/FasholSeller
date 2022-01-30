package com.fashol.seller.data.model.productdata

class CategoryDataModel(
    var error: Boolean, // false
    var message: String, // Category list
    var result: List<Result>,
    var success: Boolean // true
) {
    data class Result(
        var id: Int, // 1
        var image: String, // /defaults/vagitable.jpeg
        var name: String // Vagitables
    )
}