package com.nithesh.wordie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var menuItem: MenuItem
    private val tag: String = MainActivity::class.java.simpleName
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.inflateMenu(R.menu.search_view)
        menuItem = binding.toolbar.menu.findItem(R.id.search_view)
        searchView = menuItem.actionView as SearchView
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
        addOnDestinationChangeListener()
    }

    private fun configureSearchView(searchView: SearchView) {
        searchView.apply {
            queryHint = getString(R.string.query_hints)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val imm = getSystemService(
                        Context.INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)
                    if (!query.isNullOrEmpty()) {
                        navController.navigate(
                            WordListFragmentDirections
                                .actionWordListFragmentToSearchFragment(query)
                        )
                    } else {
                        return false
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun addOnDestinationChangeListener() {
        try {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.searchFragment
                ) {
                    menuItem.collapseActionView()
                    menuItem.isVisible = false
                    Log.i(tag, "addOnDestinationChangeListener: you can hide ")
                } else {
                    menuItem.isVisible = true
                }

            }
        } catch (t: Throwable) {
            Log.e(tag, "addOnDestinationChangeListener: ${t.message}")
        }
    }


}