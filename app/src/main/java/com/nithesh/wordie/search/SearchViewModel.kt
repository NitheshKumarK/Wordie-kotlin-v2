package com.nithesh.wordie.search

import android.util.Log
import androidx.lifecycle.*
import com.nithesh.wordie.network.Word
import com.nithesh.wordie.network.wordService
import kotlinx.coroutines.launch

//this is view model contains data to the search fragment
//it takes queryWord in constructor to load data from server as soon as
//it receives the string(queryWord)
class SearchViewModel(private var queryWord: String) : ViewModel() {

    //tag variable for logging
    private val TAG: String = SearchViewModel::class.java.simpleName

    //this is word list received from the server
    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>>
        get() = _wordList

    //this is string list receives after the word list fails to load data from server
    //if word does not exists in the server it suggest some word related to the queried word
    private val _stringList = MutableLiveData<List<String>>()
    val stringList: LiveData<List<String>>
        get() = _stringList

    //this is used to trigger the navigation event with word that triggered it
    private val _navigationWord = MutableLiveData<Word>()
    val navigationWord: LiveData<Word>
        get() = _navigationWord

    init {
        //this method is called each time the view model is initialized
        //and gets words from the server
        getWordList()
    }

    //this is called from the string adapter to restart request to server with
    //the available word
    fun searchWithNewWord(newQueryWord: String) {
        queryWord = newQueryWord
        getWordList()
    }

    //this method gets words from server asynchronously using kotlin coroutine
    private fun getWordList() {
        //launch the coroutine with view model scope
        viewModelScope.launch {
            try {
                //request server with query word
                _wordList.value = wordService.getWordListAsync(queryWord)
            } catch (t: Throwable) {
                //logs error in getWordList and to return the string list
                Log.e(TAG, "getWordList: ${t.message}")
                //it sets the wordList to emptyList instead of null to avoid null pointer exception
                _wordList.value = emptyList()
                _stringList.value = wordService.getStringList(queryWord)
            }

        }
    }

    //it sets word to the _navigationWord to trigger the navigation
    fun navigateToDetailFragment(word: Word) {
        _navigationWord.value = word
    }

    //this is prevents _navigationWord to set value again and navigate to detail fragment
    //by setting the variable to null
    fun doneSearchFragmentToDetailFragmentNavigation() {
        _navigationWord.value = null
    }

}

//this factory class helps to create the search view model with the parameter
class SearchViewModelFactory(private val queryWord: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //check if this class can be created or reused with the query word parameter
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            //return the SearchViewModel with queryWord parameter
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(queryWord) as T
        }
        // or throw an exception
        throw ClassCastException("SearchViewModel cannot be initiated")
    }
}