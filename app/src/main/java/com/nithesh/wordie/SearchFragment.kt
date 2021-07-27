package com.nithesh.wordie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.nithesh.wordie.databinding.FragmentSearchBinding
import com.nithesh.wordie.search.SearchViewModel
import com.nithesh.wordie.search.SearchViewModelFactory

class SearchFragment : Fragment() {
    //global variables
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
        //factory method to create SearchViewModel
        //with the query word
        val queryWord = SearchFragmentArgs.fromBundle(requireArguments()).queryWord
        viewModelFactory = SearchViewModelFactory(queryWord ?: "null")
        //create a SearchViewModel from ViewModelProvider
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchViewModel::class.java)


        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        navController = this.findNavController()

        //creating two adapter to resolve the error happen when query word
        //is not a word or word not found in server it returns string array
        //rather word array
        val wordAdapter = WordAdapter(viewModel)
        val stringAdapter = StringAdapter(viewModel)
        //observe the word list variable in view model
        //if list is not empty it sets default word adapter(with words list)
        //or it sets string adapter (with string list)
        viewModel.wordList.observe(viewLifecycleOwner) { words ->

            if (words.isNotEmpty()) {
                binding.recyclerView.adapter = wordAdapter
                wordAdapter.submitList(words)

            } else {
                //here recycler is set to string adapter
                binding.recyclerView.adapter = stringAdapter

                viewModel.stringList.observe(viewLifecycleOwner) { strings ->

                    stringAdapter.submitList(strings)

                }
            }
        }
        //this live data is used to observe the navigation event
        //navigate to detail fragment
        viewModel.navigationWord.observe(viewLifecycleOwner) {
            if (it != null) {
                navController.navigate(
                    SearchFragmentDirections
                        .actionSearchFragmentToDetailFragment(it)
                )//launch single top is set to false
                viewModel.doneSearchFragmentToDetailFragmentNavigation()
                Log.e(TAG, "onCreateView: word object is set after navigation is done")
            } else {
                Log.e(TAG, "onCreateView: word object is null")
            }
        }
        return binding.root
    }


}