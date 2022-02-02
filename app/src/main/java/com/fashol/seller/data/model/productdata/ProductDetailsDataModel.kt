package com.fashol.seller.data.model.productdata

data class ProductDetailsDataModel(
    var error: Boolean, // false
    var message: String, // Successfully fetched the prices!
    var result: Result,
    var success: Boolean // true
) {
    data class Result(
        var product: Product,
        var variants: List<Variant>
    ) {
        data class Product(
            var id: Int, // 1
            var name: String // deserunt
        )

        data class Variant(
            var id: Int, // 69
            var name: String, // voluptatem
            var price: Double // 57.9
        )
    }
}