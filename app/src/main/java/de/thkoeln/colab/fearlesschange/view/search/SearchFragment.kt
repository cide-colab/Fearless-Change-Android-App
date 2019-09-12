package de.thkoeln.colab.fearlesschange.view.search

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.observe
import de.thkoeln.colab.fearlesschange.core.pattern.PatternViewModelFragment
import kotlinx.android.synthetic.main.search_fragment.*


class SearchFragment : PatternViewModelFragment<SearchViewModel>(), SearchView.OnQueryTextListener {
    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
//        setHasOptionsMenu(true)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val resultAdapter = SearchPatternRecyclerViewAdapter()
        resultAdapter.onItemClickedListener = viewModel.patternCardClicked
        search_results.adapter = resultAdapter
        viewModel.pattern.observe(this) { resultAdapter.setItemsNotEquals(it) }

        val searchKeywordAdapter = SearchKeywordAutocompleteAdapter(requireContext())
        search_keyword.threshold = 1
        search_keyword.setAdapter(searchKeywordAdapter)
        search_keyword.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            searchKeywordAdapter.getItem(position)?.let { viewModel.keywordAddedListener(it) }
            search_keyword.setText("")
            hideKeyboard(requireActivity())
        }

        viewModel.unselectedKeywords.observe(this) { searchKeywordAdapter.updateKeywords(it) }

        val selectedKeywordsAdapter = SearchKeywordSwipeToDeleteRecyclerViewAdapter(requireContext())
        selectedKeywordsAdapter.afterDeleteItemListener = { item, index ->
            viewModel.onKeywordDeleted(item)
            Snackbar.make(activity_wrapper, R.string.message_keyword_removed_from_filters, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_undo) { selectedKeywordsAdapter.restoreItem(item, index) }
                    .show()
        }
        selectedKeywordsAdapter.afterRestoreItemListener = { item, _ -> viewModel.onKeywordRestored(item) }
//        selectedKeywordsAdapter.onDeleteSnackBarText = { getString(R.string.message_keyword_removed_from_filters, it.keyword) }
//        selectedKeywordsAdapter.onDeleteUndoActionText = { getString(R.string.action_undo) }
//        selectedKeywordsAdapter.beforeDeleteItemListener = viewModel.onKeywordDeleted
//        selectedKeywordsAdapter.onRestoreItemListener = viewModel.onKeywordRestored
        selected_keywords.adapter = selectedKeywordsAdapter
        viewModel.selectedKeywords.observe(this) { selectedKeywordsAdapter.setItemsNotEquals(it) }

    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(SearchViewModel::class.java)

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("SEARCH", "SUBMIT")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("SEARCH", "CHANGE")
        return true
    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
////        inflater.inflate(R.menu.search_action_menu, menu)
//
////        val searchManager = requireContext().getSystemService(SEARCH_SERVICE) as SearchManager
//        val searchMenuItem = menu.findItem(R.id.action_search)
//        val searchView = searchMenuItem.actionView as SearchView
//
////        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.isSubmitButtonEnabled = true
//
//        searchView.setOnQueryTextListener(this)
//
////        val searchView =  menu.findItem(R.id.action_search).actionView as SearchView
////        Log.d("SEARCH", "ATTACHED")
//
//    }

}