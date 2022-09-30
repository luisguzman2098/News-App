package com.luisguzman.newsapp.domain

import androidx.lifecycle.*
import com.luisguzman.newsapp.repository.bookmark.BookmarkRepository
import com.luisguzman.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import java.lang.Exception

class BookmarkViewModel(private val repository: BookmarkRepository) : ViewModel() {

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    private val _placesHolder = MutableLiveData<Boolean>()
    val placesHolder: LiveData<Boolean>
        get() = _placesHolder

    fun showLoading() {
        _progress.postValue(true)
    }

    fun hideLoading() {
        _progress.postValue(false)
    }

    fun showPlacesHolder() {
        _placesHolder.postValue(true)
    }

    fun hidePlacesHolder() {
        _placesHolder.postValue(false)
    }

    fun fetchBookmark() = liveData( Dispatchers.IO) {
        emit(Resource.Loading())
        kotlin.runCatching {
            repository.fetchBookmark()
        }.onSuccess { flowList ->
            flowList.collect {
                emit(it)
            }
        }.onFailure {
            emit(Resource.Error(Exception(it.message)))
        }
    }

}

class BookmarkViewModelFactory(private val repository: BookmarkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BookmarkRepository::class.java).newInstance(repository)
    }
}