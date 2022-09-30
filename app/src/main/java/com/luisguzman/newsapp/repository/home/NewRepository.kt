package com.luisguzman.newsapp.repository.home

import com.luisguzman.newsapp.data.model.ArticleList

interface NewRepository {
    suspend fun getNew() : ArticleList
}