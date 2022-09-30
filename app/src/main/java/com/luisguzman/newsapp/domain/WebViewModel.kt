package com.luisguzman.newsapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class WebViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun showLoading() {
        _loading.postValue(true)
    }

    fun hideLoading() {
        _loading.postValue(false)
    }

}