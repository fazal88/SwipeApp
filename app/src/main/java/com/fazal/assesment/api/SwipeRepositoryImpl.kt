package com.fazal.assesment.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fazal.assesment.model.ProductItem
import com.fazal.assesment.model.ResponseSaveProduct
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
class SwipeRepositoryImpl(
    private val api: SwipeApi
) : SwipeRepository {

    private val _listProducts = MutableLiveData<Resource<Response<List<ProductItem>>>>()
    val listProducts: LiveData<Resource<Response<List<ProductItem>>>>
        get() = _listProducts

    override suspend fun getProducts() {
        _listProducts.postValue(Resource.loading(data = null))
        try {
            _listProducts.postValue(Resource.success(data = api.getProducts()))
        } catch (exception: Exception) {
            _listProducts.postValue(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Something went wrong"
                )
            )
        }
    }


    private val _saveResponse = MutableLiveData<Resource<Response<ResponseSaveProduct>>?>()
    val saveResponse: LiveData<Resource<Response<ResponseSaveProduct>>?>
        get() = _saveResponse

    fun resetSaveResponse(){
        _saveResponse.postValue(null)
    }

    override suspend fun saveProduct(
        partMap: Map<String, RequestBody?>,
        frontImage: MultipartBody.Part?
    ) {
        _saveResponse.postValue(Resource.loading(data = null))
        try {
            _saveResponse.postValue(Resource.success(data = api.saveProduct(partMap, frontImage)))
        } catch (exception: Exception) {
            _saveResponse.postValue(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Something went wrong"
                )
            )
        }
    }

}