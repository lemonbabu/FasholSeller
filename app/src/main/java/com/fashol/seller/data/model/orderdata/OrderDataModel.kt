package com.fashol.seller.data.model.orderdata

data class OrderDataModel(
    var id: String,
    var orderid: String,
    var customerName: String,
    var tPrice: Float,
){
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass){
            return false
        }
        other as OrderDataModel
        if (id != other.id){
            return false
        }
        if (orderid != other.orderid){
            return false
        }
        if (customerName != other.customerName){
            return false
        }
        if (tPrice != other.tPrice){
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + orderid.hashCode()
        result = 31 * result + customerName.hashCode()
        result = 31 * result + tPrice.hashCode()
        return result
    }

}
