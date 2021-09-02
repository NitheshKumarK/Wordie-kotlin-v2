package com.nithesh.wordie.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nithesh.wordie.network.Word

class DetailViewModel(
    private val resultWord: Word,
    private val currentUser: FirebaseUser?
) : ViewModel() {
    private val tag = DetailViewModel::class.java.simpleName
    private var database: FirebaseDatabase
    private val collectionReference: DatabaseReference


    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word

    private val _showToast = MutableLiveData<Boolean>()
    val showToast: LiveData<Boolean>
        get() = _showToast

    init {
        _word.value = resultWord
        database = Firebase.database
        collectionReference = database.getReference("words-collection")
        Log.i(tag, "view model is initialized ")
    }

    fun saveWord() {
        if (currentUser != null) save(currentUser.uid)
        else {
            _showToast.value = true
        }
    }

    fun toastEventFinished() {
        _showToast.value = false
    }

    private fun save(currentUserId: String) {
        val userCollection = collectionReference.child(currentUserId)
        Log.i(tag, "saveWord: saving the word in real time database")

        val task = userCollection.child(resultWord.meta!!.uuid ?: collectionReference.push().key!!)
            .setValue(resultWord)
        task.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i(tag, "saveWord: ${resultWord.hwi?.hw} is successfully saved")
            }
            if (it.isComplete) {
                Log.i(tag, "saveWord: ${resultWord.hwi?.hw} is complete")
            }
            if (it.isCanceled) {
                Log.i(tag, "saveWord: ${resultWord.hwi!!.hw} is canceled ")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "onCleared: view model is cleared")
    }


}

class DetailViewModelFactory(
    private val word: Word,
    private val currentUser: FirebaseUser?
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(word, currentUser) as T
        }
        throw ClassCastException("Cannot create viewModel for this class")
    }
}