package de.thkoeln.colab.fearlesschange.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.observe
import de.thkoeln.colab.fearlesschange.core.pattern.PatternViewModelFragment
import kotlinx.android.synthetic.main.favorites_fragment.*


class FavoritesFragment : PatternViewModelFragment<FavoritesViewModel>() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.favorites_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = FavoritesSwipeToDeleteAdapter(requireContext())
        adapter.onDeleteSnackBarText = { getString(R.string.message_pattern_removed_from_fav, it.pattern.title) }
        adapter.onDeleteUndoActionText = { getString(R.string.action_undo) }
        adapter.onItemClickedListener = viewModel.patternCardClicked
        adapter.onDeleteItemAcceptedListener = viewModel.patternDeleted
        favorites_recycler_view.adapter = adapter

        viewModel.pattern.observe(this) { adapter.setItemsNotEquals(it) }
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
}
