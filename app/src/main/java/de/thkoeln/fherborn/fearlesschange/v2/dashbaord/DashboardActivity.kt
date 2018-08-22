package de.thkoeln.fherborn.fearlesschange.v2.dashbaord

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.activities.SearchActivity
import de.thkoeln.fherborn.fearlesschange.v2.AppActivity
import de.thkoeln.fherborn.fearlesschange.v2.carddetail.CardDetailDialogFragment
import de.thkoeln.fherborn.fearlesschange.v2.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.v2.viewmodel.CardViewModel
import kotlinx.android.synthetic.main.app_bar.*


class DashboardActivity : AppActivity() {

    lateinit var viewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbaord)
        setSupportActionBar(action_bar as Toolbar)
        inflateFeatureFragments()


        viewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)
        viewModel.cardClickedEvent.nonNullObserve(this) { openCardDetailPopup(it) }
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
        Log.e("Activity", "CardViewOpen")
        supportFragmentManager?.let { fm ->
            val cardPopup = CardDetailDialogFragment.newInstance(cardId = cardId)
            cardPopup.show(fm, null)
        }

    }

}