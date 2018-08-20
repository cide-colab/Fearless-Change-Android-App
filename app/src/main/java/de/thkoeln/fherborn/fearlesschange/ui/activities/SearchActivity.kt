package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.SearchKeywordRecyclerAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.KeywordRepository
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_default_app_bar.*

class SearchActivity : AppCompatActivity() {
    private lateinit var keywordRepository: KeywordRepository
    private lateinit var selectedKeywordsAdapter: SearchKeywordRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupActionBar()

        keywordRepository = KeywordRepository(application)

        keywordRepository.getAllKeywords().observe(this, Observer { keywords ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, keywords)
            search_keyword.setAdapter(adapter)
        })

        this.selectedKeywordsAdapter = SearchKeywordRecyclerAdapter()
        selected_keywords.layoutManager = LinearLayoutManager(this)
        selected_keywords.adapter = selectedKeywordsAdapter

        add_keyword.setOnClickListener {
            addKeyword()
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
        keywordRepository.getKeywordByKeyword(keywordInput).observe(this, Observer { selectedKeyword ->
            selectedKeyword?.let {
                val elementExists = selectedKeywordsAdapter.keywords.contains(selectedKeyword)
                if (!elementExists) {
                    selectedKeywordsAdapter.keywords.add(selectedKeyword)
                    selectedKeywordsAdapter.notifyDataSetChanged()
                } else {
                    // TODO: Fehler, dass Element bereits vorhanden ist
                }
            } ?: run {
                // TODO: Fehlermeldung ausgeben, dass das Keyword nicht gefunden wurde
            }
        })
    }

    private fun removeKeyword(keywordToRemove: Keyword) {
        selectedKeywordsAdapter.keywords.remove(keywordToRemove)
        selectedKeywordsAdapter.notifyDataSetChanged()
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
