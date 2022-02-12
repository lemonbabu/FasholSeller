package com.fashol.seller.data.repository.local

import com.fashol.seller.data.model.productdata.CategoryDataModel
import com.fashol.seller.data.model.productdata.ProductDataModel

object CategoryProductListData {
    var flagCat = false
    var flagPro = false
    lateinit var dataPro : List<ProductDataModel.Result>
    lateinit var dataCat : List<CategoryDataModel.Result>
}