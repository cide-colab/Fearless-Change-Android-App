package de.thkoeln.colab.fearlesschange.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.observe
import de.thkoeln.colab.fearlesschange.ui.BasicPatternFragment
import kotlinx.android.synthetic.main.search_fragment.*


class SearchFragment : BasicPatternFragment<SearchViewModel>() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val resultAdapter = SearchPatternRecyclerViewAdapter()
        resultAdapter.patternClickedListener = viewModel.patternCardClicked
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
        selectedKeywordsAdapter.onDeleteSnackBarText = { getString(R.string.message_keyword_removed_from_filters, it.keyword) }
        selectedKeywordsAdapter.onDeleteUndoActionText = { getString(R.string.action_undo) }
        selectedKeywordsAdapter.onDeleteItemListener = viewModel.onKeywordDeleted
        selectedKeywordsAdapter.onRestoreItemListener = viewModel.onKeywordRestored
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

}
