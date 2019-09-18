package de.thkoeln.colab.fearlesschange.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.observe
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import de.thkoeln.colab.fearlesschange.view.PatternCardPreviewRecyclerAdapter
import de.thkoeln.colab.fearlesschange.view.custom.MarginItemDecoration
import de.thkoeln.colab.fearlesschange.view.notes.NoteRecyclerViewAdapter
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : InteractiveFragment<SearchViewModel>(), SearchView.OnQueryTextListener {

    private var searchJob: Job? = null
    private var searchText: String? = null

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.search_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        search_fragment_cb_card.setOnCheckedChangeListener { _, _ -> refreshSearch() }
        search_fragment_cb_labels.setOnCheckedChangeListener { _, _ -> refreshSearch() }
        search_fragment_cb_note.setOnCheckedChangeListener { _, _ -> refreshSearch() }

        val noteAdapter = NoteRecyclerViewAdapter()
        noteAdapter.onTodoChanged = viewModel.updateTodo
        noteAdapter.patternClicked = viewModel.patternCardClicked
        search_fragment_notes.layoutManager = LinearLayoutManager(requireContext())
        search_fragment_notes.adapter = noteAdapter
        search_fragment_notes.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.default_padding).toInt()))
        viewModel.notes.observe(this) {
            noteAdapter.setItemsNotEquals(it)
        }

        val patternAdapter = PatternCardPreviewRecyclerAdapter()
        patternAdapter.onItemClickedListener = viewModel.patternCardClicked
        search_fragment_pattern.layoutManager = GridLayoutManager(requireContext(), 2)
        search_fragment_pattern.adapter = patternAdapter
        search_fragment_pattern.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.default_padding).toInt()))
        viewModel.patternData.observe(this) {
            patternAdapter.setItemsNotEquals(it)
        }

    }

    override fun createViewModel() = ViewModelProviders.of(this).get(SearchViewModel::class.java)

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        refreshSearch(newText)
        return false
    }

    private fun refreshSearch(newText: String? = searchText) {
        searchText = newText
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            newText?.let {
                delay(200)
                if (it.isEmpty()) {
                    viewModel.resetSearch()
                } else {
                    viewModel.onQueryTextChange(it,
                            search_fragment_cb_labels.isChecked,
                            search_fragment_cb_card.isChecked,
                            search_fragment_cb_note.isChecked
                    )
                }
            }
        }
    }


}