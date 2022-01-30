package com.fashol.seller.data.api

import com.fashol.seller.data.model.customerdata.CustomerDataModel
import com.fashol.seller.data.model.orderdata.OrderListDataModel
import com.fashol.seller.data.model.productdata.CategoryDataModel
import com.fashol.seller.data.model.productdata.ProductDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiInterfaces{

    // Customer List interface
    interface CustomerListInterface{
        @GET("common-api/auth-user-customers")
        fun getCustomerList(
            @Header("Authorization") auth: String,
        ): Call<CustomerDataModel>
    }

    // Customer List interface
//    interface CustomerListInterface{
//        @GET("common-api/auth-user-customers")
//        suspend fun getCustomerList(
//            @Header("Authorization") auth: String,
//        ): Response<ResponseBody>
//    }

    // Category List interface
    interface CategoryListInterface{
        @GET("common-api/category")
        fun getCategoryList(
            @Header("Authorization") auth: String,
        ): Call<CategoryDataModel>
    }

    // Product List interface
    interface ProductListInterface{
        @GET("common-api/products")
        fun getProductList(
            @Header("Authorization") auth: String,
        ): Call<ProductDataModel>
    }

    // Order List interface
    interface OrderListInterface{
        @GET("v1/sales-executive/orders")
        fun getOrderList(
            @Header("Authorization") auth: String,
        ): Call<OrderListDataModel>
    }

}
