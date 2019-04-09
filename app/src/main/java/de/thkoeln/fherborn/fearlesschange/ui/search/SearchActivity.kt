package de.thkoeln.fherborn.fearlesschange.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.AdapterView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.SearchViewModel
import de.thkoeln.fherborn.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.ui.patterndetail.PatternDetailDialogFragment
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppActivity() {
    private val resultsAdapter = PatternRecyclerGridAdapter()
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupActionBar()

        initViewModel()
        initSearchInput()
        initKeywordList()
        initSearchResultView()

    }

    private fun initSearchResultView() {
        resultsAdapter.patternClickedListener = { viewModel.cardPreviewClicked(it) }
        search_results.adapter = resultsAdapter
        viewModel.getSearchResult().nonNullObserve(this) { patterns ->
            resultsAdapter.updatePatterns(patterns)
            search_results.visibility = if (patterns.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun initKeywordList() {
        val selectedKeywordsAdapter = SearchKeywordRecyclerAdapter()
        viewModel.selectedKeywords.nonNullObserve(this) {
            selectedKeywordsAdapter.updateKeywords(it)
            selected_keywords.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
        }

        selected_keywords.adapter = selectedKeywordsAdapter
        selected_keywords.layoutManager = getKeywordLayoutManager()

        getRemoveKeywordTouchHelper(selectedKeywordsAdapter).attachToRecyclerView(selected_keywords)
    }

    private fun getRemoveKeywordTouchHelper(selectedKeywordsAdapter: SearchKeywordRecyclerAdapter): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                when (swipeDir) {
                    ItemTouchHelper.LEFT -> viewModel.removeKeyword(selectedKeywordsAdapter.keywords[viewHolder.adapterPosition])
                }
            }
        })
    }

    private fun getKeywordLayoutManager() = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP).apply { justifyContent = JustifyContent.FLEX_START }

    private fun initSearchInput() {
       val searchKeywordsAdapter = SearchKeywordAutocompleteAdapter(this)
        search_keyword.setAdapter(searchKeywordsAdapter)
        search_keyword.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            searchKeywordsAdapter.getItem(position)?.let { viewModel.addKeywordClicked(it) }
            search_keyword.setText("")
        }
        viewModel.getNotSelectedKeywords().observe(this, Observer {
            searchKeywordsAdapter.updateKeywords(it ?: emptyList())
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) { openCardDetail(it) }
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) { showSnackBar(it) }
    }

    private fun openCardDetail(id: Long) {
        openPopup(PatternDetailDialogFragment.newInstance(resultsAdapter.patterns.map { p -> p.pattern.id }.toLongArray(), id))
    }

    private fun setupActionBar() {
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
