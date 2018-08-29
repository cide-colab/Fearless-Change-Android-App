package de.thkoeln.fherborn.fearlesschange.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import com.google.android.flexbox.*
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.SearchViewModel
import de.thkoeln.fherborn.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.showcases.SearchShowcase
import de.thkoeln.fherborn.fearlesschange.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.ui.patterndetail.PatternDetailDialogFragment
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_search.*
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView

class SearchActivity : AppActivity() {
    private val selectedKeywordsAdapter = SearchKeywordRecyclerAdapter()
    private val resultsAdapter = PatternRecyclerGridAdapter()
    private lateinit var searchKeywordsAdapter: SearchKeywordAutocompleteAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupActionBar()

        initViewModel()
        initSearchInput()
        initKeywordList()
        initSearchResultView()

        search_button.setOnClickListener {
            viewModel.onSearchClicked()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(search_button.windowToken, 0)
        }

        SearchShowcase(this).start()
    }

    private fun initSearchResultView() {
        resultsAdapter.patternClickedListener = { viewModel.cardPreviewClicked(it)}
        search_results.adapter = resultsAdapter
        viewModel.getSearchResult().nonNullObserve(this) { patterns ->
            resultsAdapter.updatePatterns(patterns)
            search_results.visibility = if (patterns.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun initKeywordList() {
        viewModel.selectedKeywords.nonNullObserve(this) {
            selectedKeywordsAdapter.updateKeywords(it)
            selected_keywords.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
        }
        selected_keywords.adapter = selectedKeywordsAdapter
        val layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP)
        layoutManager.justifyContent = JustifyContent.FLEX_START
        selected_keywords.layoutManager = layoutManager

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

    private fun initSearchInput() {
        searchKeywordsAdapter = SearchKeywordAutocompleteAdapter(this)
        search_keyword.setAdapter(searchKeywordsAdapter)
        search_keyword.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            searchKeywordsAdapter.getItem(position)?.let { viewModel.addKeywordClicked(it) }
            search_keyword.setText("")
        }
        viewModel.getNotSelectedKeywords().observe(this, Observer {
            searchKeywordsAdapter.updateKeywords(it?: emptyList())
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) {openPopup(PatternDetailDialogFragment.newInstance(it))}
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) { showSnackBar(it) }
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu_help, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_help -> {
            MaterialShowcaseView.resetSingleUse(this, SearchShowcase.SHOWCASE_NAME)
            SearchShowcase(this).start()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
