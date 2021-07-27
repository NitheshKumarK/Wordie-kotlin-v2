package com.nithesh.wordie.wordlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.database.FirebaseDatabase
import com.nithesh.wordie.databinding.FragmentWordListBinding


class WordListFragment : Fragment() {

    private lateinit var binding: FragmentWordListBinding
    private val viewModel: WordListViewModel by viewModels()

    //private lateinit var analytics: FirebaseAnalytics
    private lateinit var database: FirebaseDatabase


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
        database = FirebaseDatabase.getInstance()
        val messageReference = database.getReference("message")
        messageReference.setValue("Hello World!")
        return binding.root
    }

}