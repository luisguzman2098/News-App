package com.luisguzman.newsapp.repository.bookmark

import com.luisguzman.newsapp.data.model.Article
import com.luisguzman.newsapp.data.remote.firebase.dataSource.BookmarkDataSource
import com.luisguzman.newsapp.utils.Resource
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(private val dataSource: BookmarkDataSource) : BookmarkRepository {
    override suspend fun fetchBookmark(): Flow<Resource<List<Article>>> = dataSource.fetchLatestPost()
}