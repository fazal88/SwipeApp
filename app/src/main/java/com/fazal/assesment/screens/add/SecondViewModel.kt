package com.fazal.assesment.screens.add

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazal.assesment.api.SwipeRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Created by Fazal on 02/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
class SecondViewModel(
    private val repo: SwipeRepositoryImpl
) : ViewModel() {

    private var _loading = MutableLiveData<String>("")
    val loading: LiveData<String>
        get() = _loading

    private val _error = MutableLiveData<String>("")
    val error: LiveData<String>
        get() = _error

    private var _navigateToList = MutableLiveData<Boolean>()
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    private var _clickedUpload = MutableLiveData<Boolean>()
    val clickedUpload: LiveData<Boolean>
        get() = _clickedUpload

    private var _clickedRemove = MutableLiveData<Boolean>()
    val clickedRemove: LiveData<Boolean>
        get() = _clickedRemove

    fun goToList() {
        _navigateToList.postValue(true)
    }

    fun upload() {
        _clickedUpload.postValue(true)
    }

    fun remove() {
        _clickedRemove.postValue(true)
    }

    val name = MutableLiveData<String>("")
    val nameError = MutableLiveData<String>("")
    val type = MutableLiveData<String>("")
    val typeError = MutableLiveData<String>("")
    val tax = MutableLiveData<String>("")
    val taxError = MutableLiveData<String>("")
    val price = MutableLiveData<String>("")
    val priceError = MutableLiveData<String>("")

    val fileUri = MutableLiveData<Uri>(Uri.parse(""))

    val saveResponse = repo.saveResponse

    fun save() {
        if (valid()) {
            val requestForm = LinkedHashMap<String, RequestBody?>()
            requestForm["product_name"] =
                name.value?.toRequestBody("text/plain".toMediaTypeOrNull())
            requestForm["product_type"] =
                type.value?.toRequestBody("text/plain".toMediaTypeOrNull())
            requestForm["tax"] = tax.value?.toRequestBody("text/plain".toMediaTypeOrNull())
            requestForm["price"] = price.value?.toRequestBody("text/plain".toMediaTypeOrNull())

            val fileRequest: MultipartBody.Part? = if (fileUri.value.toString().isNotEmpty()) {
                fileUri.value?.toFile()?.let {
                    MultipartBody.Part.createFormData(
                        "files",
                        it.name,
                        it.asRequestBody("image".toMediaTypeOrNull())
                    )
                }
            } else null

            viewModelScope.launch(Dispatchers.IO) {
                repo.saveProduct(requestForm, fileRequest)
            }
        }
    }

    private fun valid(): Boolean {
        var isValid = true
        if (name.value.isNullOrEmpty()) {
            isValid = false
            nameError.postValue("Product Name cannot be empty")
        }
        if (type.value.isNullOrEmpty()) {
            isValid = false
            typeError.postValue("Product Type cannot be empty")
        }
        if (tax.value.isNullOrEmpty()) {
            isValid = false
            taxError.postValue("tax value cannot be empty")
        } else if ((tax.value?.toInt() in 1..99).not()) {
            isValid = false
            taxError.postValue("tax value is unacceptable")
        }
        if (price.value.isNullOrEmpty()) {
            isValid = false
            priceError.postValue("Selling price cannot be empty")
        } else if (price.value?.toDouble()!! <= 0) {
            isValid = false
            priceError.postValue("Selling price is unacceptable")
        }
        return isValid
    }


    fun reset() {
        _navigateToList.postValue(false)
        _clickedUpload.postValue(false)
        _clickedRemove.postValue(false)
        _loading.postValue("")
        _error.postValue("")

    }

    fun setUri(uri: Uri) {
        fileUri.postValue(uri)
    }

    fun setError(errorText: String) {
        _error.postValue(errorText)
    }

    fun setLoadingText(loadingText: String) {
        _loading.postValue(loadingText)
    }

}