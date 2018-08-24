package de.thkoeln.fherborn.fearlesschange.v2.ui.overview

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.PatternRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.v2.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.v2.ui.carddetail.PatternDetailDialogFragment
import kotlinx.android.synthetic.main.activity_overview.*
import kotlinx.android.synthetic.main.app_bar.*


class OverviewActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        setSupportActionBar(action_bar as Toolbar)

        val adapter = PatternRecyclerGridAdapter()
        overview_recycler_view.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)

        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) { openPopup(PatternDetailDialogFragment.newInstance(it)) }
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) { showSnackBar(it) }
        viewModel.getPatterns().nonNullObserve(this) { adapter.updatePatterns(it)}

        adapter.patternClickedListener = { viewModel.cardPreviewClicked(it) }
    }

}
