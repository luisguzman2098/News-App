package com.luisguzman.newsapp.data.remote.api

import com.luisguzman.newsapp.data.model.ArticleList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewApi {

    @GET("/v2/top-headlines")
    suspend fun getNews(@Query("country") country : String, @Query("category") category : String?, @Query("apiKey") apiKey : String) : ArticleList
}