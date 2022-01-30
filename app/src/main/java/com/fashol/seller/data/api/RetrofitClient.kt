package com.fashol.seller.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val URLGlobal = "https://api.fashol.com/"
    private const val URL= "https://api.fashol.com/v1"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    //retrofit builder for get Customer List
    fun getCustomerList(): ApiInterfaces.CustomerListInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.CustomerListInterface::class.java)
    }

    //retrofit builder for get Category List
    fun getCategoryList(): ApiInterfaces.CategoryListInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.CategoryListInterface::class.java)
    }

    //retrofit builder for get Product List
    fun getProductList(): ApiInterfaces.ProductListInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.ProductListInterface::class.java)
    }

    //retrofit builder for get Product List
    fun getOrderList(): ApiInterfaces.OrderListInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.OrderListInterface::class.java)
    }

}



