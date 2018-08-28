package de.thkoeln.fherborn.fearlesschange.v2.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.SearchViewModel
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.v2.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.PatternRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.v2.ui.patterndetail.PatternDetailDialogFragment
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppActivity() {
    private val selectedKeywordsAdapter = SearchKeywordRecyclerAdapter()
    private val resultsAdapter = PatternRecyclerGridAdapter()
    private lateinit var searchKeywordsAdapter: SearchKeywordAutocompleteAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupActionBar()

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) {openPopup(PatternDetailDialogFragment.newInstance(it))}
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) { showSnackBar(it) }
        searchKeywordsAdapter = SearchKeywordAutocompleteAdapter(this)
        search_keyword.setAdapter(searchKeywordsAdapter)
        search_keyword.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            searchKeywordsAdapter.getItem(position)?.let { viewModel.addKeywordClicked(it) }
            search_keyword.setText("")
        }
        viewModel.getNotSelectedKeywords().observe(this, Observer {
            searchKeywordsAdapter.updateKeywords(it?: emptyList())
        })
        viewModel.selectedKeywords.nonNullObserve(this) {
            selectedKeywordsAdapter.updateKeywords(it)
            selected_keywords.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
        }
        selected_keywords.adapter = selectedKeywordsAdapter
        search_results.adapter = resultsAdapter

        resultsAdapter.patternClickedListener = { viewModel.cardPreviewClicked(it)}

        search_button.setOnClickListener {
            viewModel.onSearchClicked()
        }

        viewModel.getSearchResult().nonNullObserve(this) { patterns ->
            resultsAdapter.updatePatterns(patterns)
            search_results.visibility = if (patterns.isEmpty()) View.GONE else View.VISIBLE
        }


        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                when (swipeDir) {
                    ItemTouchHelper.LEFT -> viewModel.removeKeyword(selectedKeywordsAdapter.keywords[viewHolder.adapterPosition])
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
}
