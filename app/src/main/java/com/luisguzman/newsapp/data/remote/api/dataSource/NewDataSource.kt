package com.luisguzman.newsapp.data.remote.api.dataSource

import com.luisguzman.newsapp.data.model.ArticleList
import com.luisguzman.newsapp.data.remote.api.NewApi
import com.luisguzman.newsapp.utils.AppConstants.API_KEY
import com.luisguzman.newsapp.utils.AppConstants.CATEGORY
import com.luisguzman.newsapp.utils.AppConstants.COUNTRY

class NewDataSource(private val newService: NewApi) {

    suspend fun fetchNews() : ArticleList = newService.getNews(country = COUNTRY, category = CATEGORY
        , API_KEY)

}