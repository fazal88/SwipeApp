package com.fazal.assesment.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import com.fazal.assesment.api.Status
import com.fazal.assesment.api.SwipeRepositoryImpl

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
class MainViewModel(
     repo: SwipeRepositoryImpl
) : ViewModel() {

    init {

        repo.getProducts().observeForever{
            when(it.status){
                Status.SUCCESS->{
                    Log.d("TAG", "onCreate: success -> ${it.data?.body()}")
                }
                Status.ERROR->{

                    Log.d("TAG", "onCreate: error -> ${it.data?.body()}")
                }
                Status.LOADING->{

                    Log.d("TAG", "onCreate: loading -> ${it.data?.body()}")
                }
            }
            Log.d("TAG", "onCreate: ${it.data?.body()}")
        }
    }

}