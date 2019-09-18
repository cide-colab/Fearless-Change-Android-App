package de.thkoeln.colab.fearlesschange.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.observe
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.view.PatternCardPreviewRecyclerAdapter
import de.thkoeln.colab.fearlesschange.view.custom.MarginItemDecoration
import kotlinx.android.synthetic.main.favorites_fragment.*


class FavoritesFragment : InteractiveFragment<FavoritesViewModel>() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.favorites_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = PatternCardPreviewRecyclerAdapter(true)
        adapter.afterDeleteItemListener = { patternPreviewData: PatternPreviewData, position: Int ->
            viewModel.toggleFavorite(patternPreviewData)
            Snackbar.make(favorites_recycler_view, getString(R.string.message_pattern_removed_from_fav, patternPreviewData.pattern.title), Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_undo) {
                        adapter.restoreItem(patternPreviewData, position)
                        viewModel.toggleFavorite(patternPreviewData)
                    }
                    .show()
        }

        adapter.onItemClickedListener = viewModel.patternCardClicked
        favorites_recycler_view.adapter = adapter
        favorites_recycler_view.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.default_padding).toInt()))

        viewModel.pattern.observe(this) { adapter.setItemsNotEquals(it) }
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
}
