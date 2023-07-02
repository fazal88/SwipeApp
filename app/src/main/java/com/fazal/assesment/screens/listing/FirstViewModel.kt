package com.fazal.assesment.screens.listing

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
class FirstViewModel(
    private val repo : SwipeRepositoryImpl
) : ViewModel() {

    private var _navigateToAdd = MutableLiveData<Boolean>()
    val navigateToAdd: LiveData<Boolean>
        get() = _navigateToAdd

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private var _error = MutableLiveData<String>("")
    val error: LiveData<String>
        get() = _error

    val searchQuery = MutableLiveData<String>("")

    fun clearSearch(){
        searchQuery.postValue("")
    }

    fun goToAdd(){
        _navigateToAdd.postValue(true)
    }

    fun showLoading(show:Boolean){
        _loading.postValue(show)
    }

    fun setError(errorText:String){
        _error.postValue(errorText)
    }

    val list = repo.listProducts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getProducts()
        }
    }

    fun reset() {
        _navigateToAdd.postValue(false)
        _loading.postValue(false)
        _error.postValue("")
    }

}