package de.thkoeln.fherborn.fearlesschange.ui.favorites

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.ui.patterndetail.PatternDetailDialogFragment
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.action_bar.*

class FavoritesActivity : AppActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(action_bar as Toolbar)

        val adapter = PatternRecyclerGridAdapter()
        favorites_recycler_view.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)

        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) { openPopup(PatternDetailDialogFragment.newInstance(it)) }
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) { showSnackBar(it) }
        viewModel.getFavorites().nonNullObserve(this) { adapter.updatePatterns(it)}

        adapter.patternClickedListener = { viewModel.cardPreviewClicked(it) }
    }

}
