package com.nithesh.wordie.search

import androidx.lifecycle.*
import com.nithesh.wordie.network.wordService
import kotlinx.coroutines.launch


class SearchViewModel(private val query: String) : ViewModel() {
    private val _queryWord = MutableLiveData<String>()
    val queryWord: LiveData<String>
        get() = _queryWord
    val resultCount = MutableLiveData<String>()

    init {
        resultCount.value = 0.toString()
        getWordList()
    }

    private fun getWordList() {
        viewModelScope.launch {
            val list = wordService.getWordListAsync(query)
            resultCount.value = list.size.toString()
        }
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