package com.luisguzman.newsapp.repository.bookmark

import com.luisguzman.newsapp.data.model.Article
import com.luisguzman.newsapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun fetchBookmark() : Flow<Resource<List<Article>>>
}