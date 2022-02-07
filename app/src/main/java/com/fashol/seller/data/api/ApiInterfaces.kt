package com.fashol.seller.data.api

import com.fashol.seller.data.model.customerdata.CreateCustomerResponse
import com.fashol.seller.data.model.customerdata.CustomerDataModel
import com.fashol.seller.data.model.orderdata.OrderConfirmationResponse
import com.fashol.seller.data.model.orderdata.OrderListDataModel
import com.fashol.seller.data.model.productdata.CategoryDataModel
import com.fashol.seller.data.model.productdata.ProductDataModel
import com.fashol.seller.data.model.productdata.ProductDetailsDataModel
import com.fashol.seller.data.model.profile.SellerProfileDataModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*


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

    // Product List bu category interface
    interface ProductListByCategoryInterface{
        @GET("common-api/category/{id}")
        fun getProductListByCategory(
            @Path("id") id : Int,
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

    // Seller Profile interface
    interface SellerProfileInterface{
        @GET("v1/sales-executive/profile")
        fun getSellerProfile(
            @Header("Authorization") auth: String,
        ): Call<SellerProfileDataModel>
    }

    // Product details interface
    interface ProductDetailsInterface{
        @GET("common-api/customer/{customerId}/product/{productId}/fetch-price")
        fun getProductDetails(
            @Path("customerId") customerId: Int,
            @Path("productId") productId: Int,
            @Header("Authorization") auth: String,
        ): Call<ProductDetailsDataModel>
    }

    // Product details interface
    interface CreateOrderInterface{
        @POST("v1/sales-executive/orders")
        fun setNewOrder(
            @Body jsonBody: JsonObject,
            @Header("Authorization") auth: String,
        ): Call<OrderConfirmationResponse>
    }

    // Crate Customer interface
    interface CreateCustomerInterface{
        @FormUrlEncoded
        //@Multipart
        @POST("common-api/customers")
        fun createNewCustomer(
            @Field("name") name: String,
            @Field("phone") phone: String,
            @Field("store_address") storeAddress: String,
            @Field("store_name") storeName: String,
            //@Field("address") address: String,
            //@Field("status") status: String,
            @Field("zone_id") zoneId: Int,
            //@Part("profile_pic") profilePic: MultipartBody.Part,
            //@Part("store_image") storeImage: MultipartBody.Part,
            //@Part("nid_frontpart") nidFrontPart: MultipartBody.Part,
            //@Part("nid_backpart") nidBackPart: MultipartBody.Part,
            @Field("nid_number") nidNumber: String,
            @Field("dob") dob: String,
            //@Field("address_line_1") addressLine1: String,
            //@Field("address_line_2") addressLine2: String,
            //@Field("geo_location") geoLocation: String,
            @Header("Authorization") auth: String,
        ): Call<CreateCustomerResponse>
    }


}
