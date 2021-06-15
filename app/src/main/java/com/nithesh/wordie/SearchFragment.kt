package com.nithesh.wordie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.nithesh.wordie.databinding.FragmentSearchBinding
import com.nithesh.wordie.search.SearchViewModel
import com.nithesh.wordie.search.SearchViewModelFactory

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(
            inflater,
            container,
            false
        )
        viewModelFactory = SearchViewModelFactory(
            SearchFragmentArgs.fromBundle(requireArguments()).queryWord ?: "null"
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController(this)

        return binding.root
    }


}