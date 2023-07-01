package com.fazal.assesment.screens.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazal.assesment.api.SwipeRepository
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

    val list = repo.listProducts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getProducts()
        }
    }

}