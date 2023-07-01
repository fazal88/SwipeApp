package com.fazal.assesment.api

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
class SwipeRepositoryImpl(
    private val api : SwipeApi
) : SwipeRepository {

    override fun getProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = api.getProducts()))

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Something went wrong"))
        }
    }


    override fun saveProduct(request : Any) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = api.saveProduct(null)))

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Something went wrong"))
        }
    }

}