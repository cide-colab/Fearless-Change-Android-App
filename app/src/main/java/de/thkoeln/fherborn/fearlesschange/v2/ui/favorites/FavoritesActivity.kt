package de.thkoeln.fherborn.fearlesschange.v2.ui.favorites

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.PatternRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.v2.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.v2.ui.carddetail.PatternDetailDialogFragment
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.app_bar.*

class FavoritesActivity : AppActivity() {


    private val adapter = PatternRecyclerGridAdapter()

    private lateinit var viewModel: PatternViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(action_bar as Toolbar)

        favorites_recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)
        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) { openCardDetailPopup(it) }

        adapter.patternClickedListener = { viewModel.cardPreviewClicked(it) }

        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) {
            Snackbar.make(activity_wrapper, it.message, it.duration).show()
        }

        viewModel.getFavorites().nonNullObserve(this) {
            adapter.patterns = it
            adapter.notifyDataSetChanged()
        }
    }

    private fun openCardDetailPopup(cardId: Long) {
        supportFragmentManager?.let { fm ->
            val cardPopup = PatternDetailDialogFragment.newInstance(cardId = cardId)
            cardPopup.show(fm, null)
        }
    }

}
