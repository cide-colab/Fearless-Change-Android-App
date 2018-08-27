package de.thkoeln.fherborn.fearlesschange.v2.ui.search

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.SettingsViewModel
import de.thkoeln.fherborn.fearlesschange.v2.ui.AppActivity
import kotlinx.android.synthetic.main.action_bar.*

class SearchActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(action_bar as Toolbar)
        val viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
    }
}
