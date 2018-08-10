package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.CardRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardStatisticRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors.DefaultCardPreviewBehavior
import kotlinx.android.synthetic.main.activity_overview.*
import kotlinx.android.synthetic.main.layout_default_app_bar.*


class OverviewActivity : AppCompatActivity() {

    private lateinit var cardRepository: CardRepository
    private lateinit var cardActionRepository: CardStatisticRepository
    private val adapter = CardRecyclerGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        cardRepository = CardRepository(application)
        cardActionRepository = CardStatisticRepository(application)

        overview_recycler_view.adapter = adapter.apply {
            addOnCardActionListener(DefaultCardPreviewBehavior(this@OverviewActivity))
        }

        cardRepository.getAll().observe(this, Observer { cards ->
            adapter.cards = cards?: listOf()
            adapter.notifyDataSetChanged()
        })
        setupActionBar()
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_activity_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            startActivity(Intent(this, SearchActivity::class.java))
            true
        }
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
