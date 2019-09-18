package de.thkoeln.colab.fearlesschange.view

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.view.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    private var searchView: SearchView? = null
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_toolbar)
        setupNav()


    }

    private fun setupNav() {
        navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)
        navHostFragment?.navController?.apply {
            NavigationUI.setupWithNavController(bottom_nav, this)
            addOnDestinationChangedListener { _: NavController, _: NavDestination, bundle: Bundle? ->
                val hideNav = bundle?.getBoolean("hide_nav", false) ?: false
                bottom_nav.visibility = if (hideNav) GONE else VISIBLE
            }
        }
        navHostFragment?.childFragmentManager?.apply {
            addOnBackStackChangedListener {
                fragments.getOrNull(0)?.let { it as? SearchFragment }?.let {
                    searchView?.setOnQueryTextListener(it)
                } ?: searchView?.setOnQueryTextListener(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_action_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager

        searchView = (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            isSubmitButtonEnabled = false
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(arg0: View) {
                    navHostFragment?.navController?.navigate(R.id.nav_search)
                }

                override fun onViewDetachedFromWindow(arg0: View) {}
            })

        }

        return true

    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item?.itemId) {
//            R.id.action_search -> onSearchRequested()
//        }
//        return true
//    }
//
//    override fun onSearchRequested(): Boolean {
//        val appData = Bundle().apply {
//            putBoolean(JARGON, true)
//        }
//        startSearch(null, false, appData, false)
//        return true
//    }

}
