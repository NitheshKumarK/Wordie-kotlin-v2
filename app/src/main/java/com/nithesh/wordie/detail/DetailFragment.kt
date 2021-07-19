package com.nithesh.wordie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nithesh.wordie.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
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
        val viewModel: DetailViewModel by viewModels { factory }
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }



}