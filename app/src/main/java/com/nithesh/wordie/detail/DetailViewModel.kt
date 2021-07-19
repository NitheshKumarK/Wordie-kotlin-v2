package com.nithesh.wordie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nithesh.wordie.network.Word

class DetailViewModel(resultWord: Word) : ViewModel() {
    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word

    init {
        _word.value = resultWord
    }

}

class DetailViewModelFactory(private val word: Word) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(word) as T
        }
        throw ClassCastException("Cannot create viewModel for this class")
    }
}