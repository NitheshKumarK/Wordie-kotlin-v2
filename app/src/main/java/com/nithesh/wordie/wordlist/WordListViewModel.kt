package com.nithesh.wordie.wordlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nithesh.wordie.network.wordService
import kotlinx.coroutines.launch


private val TAG: String = WordListViewModel::class.java.simpleName


class WordListViewModel : ViewModel() {


    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    init {
        _text.value = "viewModel created"
    }


    fun getWordList() {
        viewModelScope.launch {
            try {
                val list = wordService.getWordListAsync("apple")
                _text.value = list.size.toString()
            } catch (t: Throwable) {
                Log.e(TAG, "getWordList: $t")
                _text.value = "error $t"
            }
        }
    }
}