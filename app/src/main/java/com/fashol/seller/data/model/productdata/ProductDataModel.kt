package com.fashol.seller.data.model.productdata

class ProductDataModel (
    var error: Boolean, // false
    var message: String, // Product Successfully Fetch!
    var result: List<Result>,
    var success: Boolean // true
) {
    data class Result(
        var category: Category,
        var category_id: Int, // 1
        var description: String, // Repellendus nihil vitae similique accusantium illum. Consequatur odio quidem non. Magnam rem omnis ullam. Laudantium aut nihil quasi. Et omnis magnam et.
        var id: Int, // 1
        var image_path: String, // https://via.placeholder.com/200x200.png/009944?text=est
        var name: String, // deserunt
        var packaging_unit: String, // 74.44
        var sku: String, // SKU-3519
        var tags: List<String>,
        var unit: Unit,
        var unit_id: Int, // 1
        var variants: List<Variant>
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

        data class Variant(
            var id: Int, // 69
            var name: String // voluptatem
        )
    }
}