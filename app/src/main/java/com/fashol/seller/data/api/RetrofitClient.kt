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
    fun getProductListByCategory(): ApiInterfaces.ProductListByCategoryInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.ProductListByCategoryInterface::class.java)
    }

    //retrofit builder for get Order List
    fun getOrderList(): ApiInterfaces.OrderListInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.OrderListInterface::class.java)
    }

    //retrofit builder for get Product Details
    fun getProductDetails(): ApiInterfaces.ProductDetailsInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.ProductDetailsInterface::class.java)
    }

    //retrofit builder for create Order
    fun newOrder(): ApiInterfaces.CreateOrderInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.CreateOrderInterface::class.java)
    }

    //retrofit builder for create new customer
    fun createNewCustomer(): ApiInterfaces.CreateCustomerInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.CreateCustomerInterface::class.java)
    }

    //retrofit builder for seller profile
    fun getSellerProfile(): ApiInterfaces.SellerProfileInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.SellerProfileInterface::class.java)
    }

    //retrofit builder for Order Details
    fun getOrderDetails(): ApiInterfaces.OrderDetailsInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.OrderDetailsInterface::class.java)
    }

}



