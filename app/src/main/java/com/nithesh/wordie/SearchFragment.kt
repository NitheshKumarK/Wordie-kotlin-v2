package com.nithesh.wordie


import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.nithesh.wordie.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var activity: Activity
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
        activity = requireActivity()
        activity.actionBar?.setDisplayShowTitleEnabled(false)
        navController = findNavController()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_view_menu, menu)
        createSearchMenu(menu)
    }

    private fun createSearchMenu(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.search_menu)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.apply {
            isSubmitButtonEnabled = true
            setIconifiedByDefault(false)
            queryHint = "Search Words..."
            onSub
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }


}