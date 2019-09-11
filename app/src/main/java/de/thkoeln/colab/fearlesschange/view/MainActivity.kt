package de.thkoeln.colab.fearlesschange.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import de.thkoeln.colab.fearlesschange.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupNav()


    }

    private fun setupNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottom_nav, navHostFragment.navController)
        navHostFragment.navController.addOnDestinationChangedListener { _: NavController, _: NavDestination, bundle: Bundle? ->
            val hideNav = bundle?.getBoolean("hide_nav", false) ?: false
            bottom_nav.visibility = if (hideNav) GONE else VISIBLE
        }
    }
}
