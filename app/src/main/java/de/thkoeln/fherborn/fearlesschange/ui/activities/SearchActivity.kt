package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.CardRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.adapters.SearchKeywordRecyclerAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.KeywordRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors.DefaultCardPreviewBehavior
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_default_app_bar.*

class SearchActivity : AppCompatActivity() {
    private lateinit var keywordRepository: KeywordRepository
    private lateinit var cardRepository: CardRepository
    private lateinit var selectedKeywordsAdapter: SearchKeywordRecyclerAdapter
    private val resultsAdapter = CardRecyclerGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupActionBar()

        keywordRepository = KeywordRepository(application)
        cardRepository = CardRepository(application)

        keywordRepository.getAllKeywords().observe(this, Observer { keywords ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, keywords)
            search_keyword.setAdapter(adapter)
        })

        this.selectedKeywordsAdapter = SearchKeywordRecyclerAdapter()
        selected_keywords.layoutManager = LinearLayoutManager(this)
        selected_keywords.adapter = selectedKeywordsAdapter

        search_error.visibility = View.GONE

        search_results.adapter = resultsAdapter.apply {
            addBehaviors(DefaultCardPreviewBehavior(this@SearchActivity))
        }

        add_keyword.setOnClickListener {
            addKeyword()
        }

        search_button.setOnClickListener {
            search()
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                when (swipeDir) {
                    ItemTouchHelper.LEFT -> removeKeyword(selectedKeywordsAdapter.keywords[viewHolder.adapterPosition])
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(selected_keywords)
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addKeyword() {
        val keywordInput: String = search_keyword.text.toString()
        search_error.text = ""
        search_error.visibility = View.GONE
        keywordRepository.getKeywordByKeyword(keywordInput).observe(this, Observer { selectedKeyword ->
            selectedKeyword?.let {
                val elementExists = selectedKeywordsAdapter.keywords.contains(selectedKeyword)
                if (!elementExists) {
                    selectedKeywordsAdapter.keywords.add(selectedKeyword)
                    selectedKeywordsAdapter.notifyDataSetChanged()
                    search_keyword.setText("")
                } else {
                    search_error.visibility = View.VISIBLE
                    search_error.text = getString(R.string.search_error_already_in_list)
                }
            } ?: run {
                search_error.visibility = View.VISIBLE
                search_error.text = getString(R.string.search_error_keyword_not_found)
            }
        })
    }

    private fun removeKeyword(keywordToRemove: Keyword) {
        selectedKeywordsAdapter.keywords.remove(keywordToRemove)
        selectedKeywordsAdapter.notifyDataSetChanged()
    }

    private fun search() {
        search_error.visibility = View.GONE
        val keywordIds = selectedKeywordsAdapter.keywords.map { k -> k.id }
        cardRepository.getCardsByKeywords(keywordIds).observe(this, Observer { cards ->
            cards?.let {
                resultsAdapter.cards = cards
                println(cards)
                resultsAdapter.notifyDataSetChanged()
                if (cards.isEmpty()) {
                    search_error.visibility = View.VISIBLE
                    search_error.text = getString(R.string.search_error_no_cards_found)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_activity_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_refresh -> {

            true
        }
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
