package com.fazal.assesment.api

import com.fazal.assesment.model.ProductItem
import com.fazal.assesment.model.ResponseSaveProduct
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
interface SwipeApi {

    @GET("api/public/get")
    suspend fun getProducts() : Response<List<ProductItem>>

    @POST("api/public/add")
    @Multipart
    suspend fun saveProduct(
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody?>,
        @Part frontImage: MultipartBody.Part?
    ) : Response<ResponseSaveProduct>

}