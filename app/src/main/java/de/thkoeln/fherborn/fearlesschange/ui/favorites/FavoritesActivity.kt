package de.thkoeln.fherborn.fearlesschange.ui.favorites

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
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.action_bar.*

class FavoritesActivity : AppActivity() {

    private val adapter = PatternRecyclerGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favorites_recycler_view.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)

        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) { data ->
            openCardDetailPopup(data.first, data.second)
        }
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) { showSnackBar(it) }
        viewModel.getFavorites().nonNullObserve(this) {
            adapter.updatePatterns(it)
        }

        adapter.patternClickedListener = {
            viewModel.cardPreviewClicked(adapter.patterns.map { p -> p.pattern.id }.toLongArray(), it?.pattern?.id)
        }
    }

    private fun openCardDetailPopup(ids: LongArray, selected: Long) {
        supportFragmentManager?.let { fm ->
            val cardPopup = PatternDetailDialogFragment.newInstance(ids, selected)
            cardPopup.onDismissListener = {
                favorites_recycler_view.smoothScrollToPosition(adapter.patterns.indexOfFirst { item -> item.pattern.id == it })
            }
            cardPopup.show(fm, null)
        }
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
