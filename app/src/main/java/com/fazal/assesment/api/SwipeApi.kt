package com.fazal.assesment.api

import com.fazal.assesment.model.ProductItem
import com.fazal.assesment.model.ResponseSaveProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
interface SwipeApi {

    @GET("api/public/get")
    suspend fun getProducts() : Response<List<ProductItem>>

    @POST("api/public/add")
    suspend fun saveProduct(request : ResponseSaveProduct?) : Response<ResponseSaveProduct>

}