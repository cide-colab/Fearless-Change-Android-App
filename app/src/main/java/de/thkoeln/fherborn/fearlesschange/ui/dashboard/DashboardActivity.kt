package de.thkoeln.fherborn.fearlesschange.ui.dashboard

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.ui.AppActivity
import de.thkoeln.fherborn.fearlesschange.ui.dashboard.DashboardFeatureRegistry.dashboardFeatures
import de.thkoeln.fherborn.fearlesschange.ui.patterndetail.PatternDetailDialogFragment
import de.thkoeln.fherborn.fearlesschange.ui.search.SearchActivity
import de.thkoeln.fherborn.fearlesschange.showcases.DashboardShowcase
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_dashbaord.*
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView


class DashboardActivity : AppActivity() {

    private lateinit var viewModel: PatternViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbaord)
        setSupportActionBar(action_bar as Toolbar)
        collapsing_toolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
        inflateFeatureFragments()


        viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)
        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) { openCardDetailPopup(it) }
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) {
            Snackbar.make(activity_wrapper, it.message, it.duration).show()
        }
    }

    override fun onResume() {
        super.onResume()
        DashboardShowcase(this).start()
    }

    private fun inflateFeatureFragments() {

        val features = dashboardFeatures
        val ft = supportFragmentManager.beginTransaction()
        features.forEach {
            ft.add(R.id.dashboard_feature_list, it, null)
        }
        ft.commit()
    }

    private fun openCardDetailPopup(cardId: Long) {
        supportFragmentManager?.let { fm ->
            val cardPopup = PatternDetailDialogFragment.newInstance(cardId = cardId)
            cardPopup.show(fm, null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu_help, menu)
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            startActivity(Intent(this, SearchActivity::class.java))
            true
        }
        R.id.action_help -> {
            MaterialShowcaseView.resetSingleUse(this, DashboardShowcase.SHOWCASE_NAME)
            DashboardShowcase(this).start()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
