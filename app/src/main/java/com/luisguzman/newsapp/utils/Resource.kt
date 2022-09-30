package com.luisguzman.newsapp.utils

sealed class Resource<out T> {
    class Loading<out T>(): Resource<T>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val exception: Exception): Resource<Nothing>()
}
