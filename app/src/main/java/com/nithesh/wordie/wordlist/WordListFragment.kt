package com.nithesh.wordie.wordlist

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nithesh.wordie.databinding.FragmentWordListBinding


class WordListFragment : Fragment() {

    private lateinit var binding: FragmentWordListBinding
    private val viewModel: WordListViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private var isUserAnonymous = false

    //firebase authentication
    //sign-in launcher
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) {
        this.onSignInResult(it)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWordListBinding.inflate(
            inflater,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //initialize the firebase analytics
        //analytics = Firebase.analytics
        //log prescribed events to firebase analytics
        //analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM){
        //param(FirebaseAnalytics.Param.ITEM_ID,1)
//            param(FirebaseAnalytics.Param.ITEM_NAME,"word")
//            param(FirebaseAnalytics.Param.CONTENT_TYPE,"String")
//        }
        //initialize the fire-base realtime-database
        auth = Firebase.auth

        binding.startButton.setOnClickListener {
            launchActivity()
        }
        binding.logoutButton.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireActivity())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.textView.text = "Successfully signed out"
                    }
                }
        }
        auth.addAuthStateListener {
            if (it.currentUser == null) {
                launchActivity()
            } else {
                if (it.currentUser?.isAnonymous == true) {
                    isUserAnonymous = true
                    Toast.makeText(
                        this.context,
                        "Your data will be deleted after your exit the app",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isUserAnonymous) {
            Firebase.database.getReference("words-collection")
                .child(auth.currentUser?.uid!!).removeValue()
            AuthUI.getInstance().delete(this.requireContext())
        }
    }

    private fun launchActivity() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == Activity.RESULT_OK) {
            val currentUser = auth.currentUser
            viewModel.setTextValue(currentUser?.uid ?: "unique user id is empty")
        } else {
            viewModel.setTextValue(" signIn failed")
        }
    }

}