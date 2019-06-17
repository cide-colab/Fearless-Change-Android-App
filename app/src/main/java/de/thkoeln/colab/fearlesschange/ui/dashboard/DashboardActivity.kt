package de.thkoeln.colab.fearlesschange.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.colab.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.colab.fearlesschange.ui.AppActivity
import de.thkoeln.colab.fearlesschange.ui.dashboard.DashboardFeatureRegistry.dashboardFeatures
import de.thkoeln.colab.fearlesschange.ui.pattern.PatternDetailDialogFragment
import de.thkoeln.colab.fearlesschange.ui.search.SearchActivity
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_dashbaord.*


class DashboardActivity : AppActivity() {

    private lateinit var viewModel: PatternViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbaord)
        setSupportActionBar(action_bar as Toolbar)
        collapsing_toolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
        inflateFeatureFragments()


        viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)
        viewModel.openPatternDetailDialogEvent.nonNullObserve(this) { data ->
            openCardDetailPopup(data.first, data.second)
        }
        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) {
            Snackbar.make(activity_wrapper, it.message, it.duration).show()
        }
    }

    private fun inflateFeatureFragments() {

        val features = dashboardFeatures
        val ft = supportFragmentManager.beginTransaction()
        features.forEach {
            ft.add(R.id.dashboard_feature_list, it, null)
        }
        ft.commit()
    }

    private fun openCardDetailPopup(ids: LongArray, selected: Long) {
        supportFragmentManager.let { fm ->
            val cardPopup = PatternDetailDialogFragment.newInstance(ids, selected)
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
