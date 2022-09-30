package com.luisguzman.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    val id: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("source")
    val source: Source? = null,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val urlToImage: String = "",
    @SerializedName("publishedAt")
    val publishedAt: String = "",
    @SerializedName("content")
    val content: String = "",
    var isBookmark: Boolean = false
)