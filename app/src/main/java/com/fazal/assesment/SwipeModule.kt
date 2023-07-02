package com.fazal.assesment

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fazal.assesment.api.SwipeApi
import com.fazal.assesment.api.SwipeRepositoryImpl
import com.fazal.assesment.screens.MainViewModel
import com.fazal.assesment.screens.add.SecondViewModel
import com.fazal.assesment.screens.listing.FirstViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
val appModule = module {
    single {
        HttpLoggingInterceptor().apply {
            this.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(
                ChuckerInterceptor.Builder(androidContext())
                    .collector(ChuckerCollector(androidContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://app.getswipe.in/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwipeApi::class.java)
    }
    single<SwipeRepositoryImpl> {
        SwipeRepositoryImpl(get())
    }
    viewModel<FirstViewModel> {
        FirstViewModel(get())
    }
    viewModel<SecondViewModel> {
        SecondViewModel(get())
    }
}
