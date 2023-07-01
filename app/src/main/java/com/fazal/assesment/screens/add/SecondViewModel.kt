package com.fazal.assesment.screens.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazal.assesment.api.SwipeRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Fazal on 02/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
class SecondViewModel(
    private val repo : SwipeRepositoryImpl
) : ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>("")
    val error: LiveData<String>
        get() = _error

    private var _navigateToList = MutableLiveData<Boolean>()
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList
    fun goToList(){
        _navigateToList.postValue(true)
    }

    val name = MutableLiveData<String>("")
    val nameError = MutableLiveData<String>("")
    val type = MutableLiveData<String>("")
    val typeError = MutableLiveData<String>("")
    val tax = MutableLiveData<String>("")
    val taxError= MutableLiveData<String>("")
    val price = MutableLiveData<String>("")
    val priceError = MutableLiveData<String>("")

    val saveResponse = repo.saveResponse

    fun save(){
        if(valid()){
            //save
            goToList()
        }
    }

    private fun valid(): Boolean {
        var isValid = true
        if(name.value.isNullOrEmpty()){
            isValid = false
            nameError.postValue("Product Name cannot be empty")
        }
        if(type.value.isNullOrEmpty()){
            isValid = false
            typeError.postValue("Product Type cannot be empty")
        }
        if(tax.value.isNullOrEmpty()){
            isValid = false
            taxError.postValue("tax value cannot be empty")
        }else if((tax.value?.toInt() in 1..99).not()){
            isValid = false
            taxError.postValue("tax value is unacceptable")
        }
        if(price.value.isNullOrEmpty()){
            isValid = false
            priceError.postValue("Selling price cannot be empty")
        }else if(price.value?.toDouble()!! <= 0){
            isValid = false
            priceError.postValue("Selling price is unacceptable")
        }
        return isValid
    }


    fun reset() {
        _navigateToList.postValue(false)
        _loading.postValue(false)
        _error.postValue("")
    }

}