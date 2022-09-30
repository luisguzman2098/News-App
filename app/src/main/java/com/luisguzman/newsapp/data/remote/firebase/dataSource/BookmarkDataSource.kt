package com.luisguzman.newsapp.data.remote.firebase.dataSource

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.luisguzman.newsapp.data.model.Article
import com.luisguzman.newsapp.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BookmarkDataSource {

    suspend fun fetchLatestPost() : Flow<Resource<List<Article>>> = callbackFlow {
        val articleList = mutableListOf<Article>()

        var bookmarkReference:Query? = null

        try {
            bookmarkReference = Firebase.firestore.collection("bookmarks")
        } catch (e: Throwable) {
            close(e)
        }

        val suscription = bookmarkReference?.addSnapshotListener { value, error ->
            if (value == null) return@addSnapshotListener
            try {
                articleList.clear()
                for (article in value.documents) {
                    article.toObject(Article::class.java).let {
                        if (it != null) {
                            articleList.add(it)
                        }
                    }
                }
            } catch (e:Exception) {
                close(e)
            }
                trySend(Resource.Success(articleList))
            }
        awaitClose { suscription?.remove() }
        }
}

