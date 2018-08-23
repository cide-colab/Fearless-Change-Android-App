package de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.activities.SearchActivity
import de.thkoeln.fherborn.fearlesschange.v2.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.v2.ui.carddetail.PatternDetailDialogFragment
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.DashbaordFeatureRegistry.dashboardFeatures
import kotlinx.android.synthetic.main.app_bar.*


class DashboardActivity : AppActivity() {

    lateinit var viewModel: PatternViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbaord)
        setSupportActionBar(action_bar as Toolbar)
        inflateFeatureFragments()


        viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)
        viewModel.patternPreviewClickedEvent.nonNullObserve(this) { openCardDetailPopup(it) }
    }

    private fun inflateFeatureFragments() {

        val features = dashboardFeatures
        val ft = supportFragmentManager.beginTransaction()
        features.forEach {
            ft.add(R.id.dashboard_feature_list, it, null)
        }
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_activity_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            startActivity(Intent(this, SearchActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun openCardDetailPopup(cardId: Long) {
        supportFragmentManager?.let { fm ->
            val cardPopup = PatternDetailDialogFragment.newInstance(cardId = cardId)
            cardPopup.show(fm, null)
        }

    }

}
