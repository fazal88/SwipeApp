package com.fazal.assesment.api

import androidx.lifecycle.LiveData
import com.fazal.assesment.model.ProductItem
import com.fazal.assesment.model.ResponseSaveProduct
import retrofit2.Response

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
interface SwipeRepository {

     fun getProducts() : LiveData<Resource<Response<List<ProductItem>>>>

     fun saveProduct(request : Any): LiveData<Resource<Response<ResponseSaveProduct>>>

}