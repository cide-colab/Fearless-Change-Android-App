package de.thkoeln.fherborn.fearlesschange.v2.ui.search

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.SettingsViewModel
import de.thkoeln.fherborn.fearlesschange.v2.ui.AppActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.action_bar.*

class SearchActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(action_bar as Toolbar)
        val viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            startActivity(Intent(this, SearchActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
