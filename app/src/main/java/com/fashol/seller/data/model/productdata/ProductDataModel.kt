package com.fashol.seller.data.model.productdata


class ProductDataModel (
    var id: String,
    var avatar: String,
    var name: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass){
            return false
        }
        other as ProductDataModel
        if (id != other.id){
            return false
        }
        if (avatar != other.avatar){
            return false
        }
        if (name != other.name){
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + avatar.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}