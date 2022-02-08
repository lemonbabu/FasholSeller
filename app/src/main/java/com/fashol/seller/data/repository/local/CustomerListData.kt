package com.fashol.seller.data.repository.local

import com.fashol.seller.data.model.customerdata.CustomerDataModel

object CustomerListData {
    var flag = false
    lateinit var data : List<CustomerDataModel.Result>
}