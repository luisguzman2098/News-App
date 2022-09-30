package com.luisguzman.newsapp.repository.home

import com.luisguzman.newsapp.data.model.ArticleList
import com.luisguzman.newsapp.data.remote.api.dataSource.NewDataSource
import com.luisguzman.newsapp.repository.home.NewRepository

class NewRepositoryImpl(private val dataSource: NewDataSource) : NewRepository {
    override suspend fun getNew(): ArticleList = dataSource.fetchNews()
}