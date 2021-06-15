package com.nithesh.wordie.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SearchViewModel(queryWord: String) : ViewModel() {
    private val _queryWord = MutableLiveData<String>()
    val queryWord: LiveData<String>
        get() = _queryWord

    init {
        _queryWord.value = queryWord
    }
}

class SearchViewModelFactory(private val queryWord: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(queryWord) as T
        }
        throw ClassCastException("SearchViewModel cannot be initiated")
    }
}