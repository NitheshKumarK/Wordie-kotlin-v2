package com.nithesh.wordie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.nithesh.wordie.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private val TAG: String = DetailFragment::class.java.simpleName
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var viewModel: Lazy<DetailViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )
        val word = DetailFragmentArgs.fromBundle(requireArguments()).detailWord
        val factory = DetailViewModelFactory(word!!)
        viewModel = viewModels { factory }
        binding.viewModel = viewModel.value
        analytics = Firebase.analytics
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, word.meta!!.id ?: "-1")
            param(FirebaseAnalytics.Param.CONTENT, word.hwi!!.hw ?: "no head word")
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "word")
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}