package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.getResourceId
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import kotlinx.android.synthetic.main.activity_card_detail.*

class CardDetailActivity : AppCompatActivity() {

    private lateinit var cardRepository: CardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        setupActionBar()

        val id = getIdFromIntent()

        cardRepository = CardRepository(application)
        cardRepository.getById(id).observe(this, Observer {
            it?.let { setCardValues(it) }
        })
    }

    private fun setCardValues(card: Card) {
        card_image.setImageResource(
                getResourceId(this, card.pictureName, "drawable", packageName)?.also { Log.e("TEST", it.toString()) }
                ?:R.drawable.img_placeholder
        )
        card_title.text = card.title
        card_problem.text = card.problem
        card_solution.text = card.solution
        card_buts.text = card.buts
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_activity_card_detail)
    }

    private fun getIdFromIntent(): Long {
        if(!intent.hasExtra(CARD_ID_KEY)) throw IllegalArgumentException("Missing card id in intent")
        return intent.getLongExtra(CARD_ID_KEY, -1)
    }

    companion object {
        const val CARD_ID_KEY = "cardId"
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
