package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.CardRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors.DefaultCardPreviewBehavior
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppActivity() {


    private lateinit var cardRepository: CardRepository
    private val adapter = CardRecyclerGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        cardRepository = CardRepository(application)

        favorites_recycler_view.adapter = adapter.apply {
            addBehaviors(DefaultCardPreviewBehavior(this@FavoritesActivity))
        }

        cardRepository.getFavoritesWithNoteCount().observe(this, Observer { cards ->
            adapter.cards = cards ?: listOf()
            adapter.notifyDataSetChanged()
        })
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
