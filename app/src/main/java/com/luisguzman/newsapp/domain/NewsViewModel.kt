package com.luisguzman.newsapp.domain

import androidx.lifecycle.*
import com.luisguzman.newsapp.repository.home.NewRepository
import com.luisguzman.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class NewsViewModel(private val repository: NewRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun showLoading() {
        _loading.postValue(true)
    }

    fun hideLoading() {
        _loading.postValue(false)
    }

    fun fetchNews() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getNew()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}

class NewsViewModelFactory(private val repository: NewRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NewRepository::class.java).newInstance(repository)
    }
}