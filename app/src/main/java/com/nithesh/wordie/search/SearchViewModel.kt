package com.nithesh.wordie.search

import android.util.Log
import androidx.lifecycle.*
import com.nithesh.wordie.network.Word
import com.nithesh.wordie.network.wordService
import kotlinx.coroutines.launch


class SearchViewModel(private val query: String) : ViewModel() {
    private val TAG: String = SearchViewModel::class.java.simpleName
    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>>
        get() = _wordList

    private val _stringList = MutableLiveData<List<String>>()
    val stringList: LiveData<List<String>>
        get() = _stringList


    init {
        getWordList()
    }

    private fun getWordList() {
        viewModelScope.launch {
            try {
                _wordList.value = wordService.getWordListAsync(query)
            } catch (t: Throwable) {
                Log.e(TAG, "getWordList: ${t.message}")
                _wordList.value = emptyList()
                _stringList.value = wordService.getStringList(query)
            }

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