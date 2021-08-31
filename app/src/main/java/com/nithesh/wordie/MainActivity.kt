package com.nithesh.wordie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.nithesh.wordie.databinding.ActivityMainBinding
import com.nithesh.wordie.wordlist.WordListFragmentDirections

class MainActivity : AppCompatActivity() {
    //these are global variables
    //views
    private lateinit var searchMenuItem: MenuItem
    private lateinit var saveMenuItem: MenuItem
    private lateinit var searchView: SearchView

    //binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    //constants
    private val tag: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate layout using DataBinding
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        //in activity use supportFragmentManager to get navHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //from navHostFragment find navController
        navController = navHostFragment.navController
        //appBarConfiguration to change icon of the app bar and for navigation drawer
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        //setting toolbar with navController, appBarConfiguration, and
        // to use the menu item with action class
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        //inflate search menu into the toolbar
        binding.toolbar.inflateMenu(R.menu.search_view)
        //find search menu item from the menu
        searchMenuItem = binding.toolbar.menu.findItem(R.id.search_menu)
        //find the save menu item from the menu
        //get search view from the search menu item
        searchView = searchMenuItem.actionView as SearchView
        //get save button from the save menu item
        //we are using the actionViewClass in search menu
        //so we have to configure the search view
        configureSearchView(searchView)
//        menuItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener{
//            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
//                binding.toolbar.title = null
//                binding.invalidateAll()
//                return true
//            }
//
//            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
//                binding.toolbar.title = getString(R.string.app_name)
//                binding.invalidateAll()
//                return true
//            }
//        })
        //add destinationChangeListener to navController by calling this method
        addOnDestinationChangeListener()


    }


    private fun configureSearchView(searchView: SearchView) {
        searchView.apply {
            queryHint = getString(R.string.query_hints) //sets the query hint
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //get inputMethodManager from system service to hide the softKeyboard
                    val inputMethodManager = getSystemService(
                        Context.INPUT_METHOD_SERVICE

                    ) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
                    //check if query is null or empty
                    //if not navigate to search fragment with argument
                    if (!query.isNullOrEmpty()) {
                        navController.navigate(
                            WordListFragmentDirections
                                .actionWordListFragmentToSearchFragment(query)
                        )
                    } else {
                        return false //false to perform default action
                    }
                    return true // true lets searchView know that text is changed
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    //this method does nothing
                    return false
                }
            })
        }
    }

    private fun addOnDestinationChangeListener() {
        try {
            //navController, destination, argument are the three arguments
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.searchFragment -> {
                        searchMenuItem.collapseActionView()
                        searchMenuItem.isVisible = false
                        Log.i(tag, "addOnDestinationChangeListener: you can hide ")
                    }
                    R.id.detailFragment -> {
                        //this is untested in real device
                        //if it does it should hide the search View and to view the save view
                        //and i tested it works fine
                        searchMenuItem.actionView.visibility = View.GONE
                    }
                    else -> {
                        searchMenuItem.isVisible = true
                        searchMenuItem.actionView.visibility = View.VISIBLE
                    }
                }

            }
        } catch (t: Throwable) {
            Log.e(tag, "addOnDestinationChangeListener: ${t.message}")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == saveMenuItem.itemId) {
            Log.i(TAG, "onOptionsItemSelected: save menu is clicked")
        }
        return true
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp() || navController.navigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navController.navigateUp()
    }

    companion object {
        private val TAG: String = MainActivity::class.java.simpleName
    }

}
