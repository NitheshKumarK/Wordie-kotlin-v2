package com.nithesh.wordie

import android.os.Bundle
import android.util.Log
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
    private val TAG: String = SearchFragment::class.java.simpleName


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
        val wordAdapter = RecyclerViewAdapter()
        val stringAdapter = StringAdapter()
        viewModel.wordList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.recyclerView.adapter = wordAdapter
                Log.i(TAG, "onCreateView: word adapter is added to recycler view")
                wordAdapter.submitList(it)
                Log.i(TAG, "onCreateView: word list is submitted")
            } else {
                binding.recyclerView.adapter = stringAdapter
                Log.i(TAG, "onCreateView: string adapter is added to recycler view")
                viewModel.stringList.observe(viewLifecycleOwner) { strings ->
                    Log.i(TAG, "onCreateView: string list is listening ")
                    stringAdapter.submitList(strings)
                    Log.i(TAG, "onCreateView: strings is submitted to string adapter")
                }
            }
        }

        return binding.root
    }


}