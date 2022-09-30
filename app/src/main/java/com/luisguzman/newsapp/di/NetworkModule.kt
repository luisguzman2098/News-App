package com.luisguzman.newsapp.di

import com.google.gson.GsonBuilder
import com.luisguzman.newsapp.data.remote.api.NewApi
import com.luisguzman.newsapp.utils.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    val newService: NewApi by lazy {
       Retrofit.Builder()
           .baseUrl(AppConstants.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
           .build()
           .create(NewApi::class.java)
    }
}