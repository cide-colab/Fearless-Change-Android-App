package de.thkoeln.colab.fearlesschange.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.observe
import de.thkoeln.colab.fearlesschange.ui.BasicPatternFragment
import kotlinx.android.synthetic.main.favorites_fragment.*


class FavoritesFragment : BasicPatternFragment<FavoritesViewModel>() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.favorites_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = FavoritesSwipeToDeleteAdapter(requireContext(), requireContext().getString(R.string.removed_from_fav))
        adapter.patternClickedListener = viewModel.patternCardClicked
        adapter.onItemDeletedListener = viewModel.patternDeleted
        favorites_recycler_view.adapter = adapter

        viewModel.pattern.observe(this) { adapter.setItems(it) }
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
}
