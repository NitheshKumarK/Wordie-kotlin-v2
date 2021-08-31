package com.nithesh.wordie.wordlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordListViewModel : ViewModel() {


    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    fun setTextValue(value: String) {
        _text.value = value
    }

    init {
        _text.value = "Word list fragment"
    }

}
