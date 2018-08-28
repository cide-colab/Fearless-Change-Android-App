package de.thkoeln.fherborn.fearlesschange.ui.overview

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.ui.patterndetail.PatternDetailDialogFragment
import de.thkoeln.fherborn.fearlesschange.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_overview.*
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.pattern_detail_dialog.*


class OverviewActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val adapter = PatternRecyclerGridAdapter()
        overview_recycler_view.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)

        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) { openPopup(PatternDetailDialogFragment.newInstance(it)) }
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) { showSnackBar(it) }
        viewModel.getPatterns().nonNullObserve(this) { adapter.updatePatterns(it)}

        adapter.patternClickedListener = { viewModel.cardPreviewClicked(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            startActivity(Intent(this, SearchActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
