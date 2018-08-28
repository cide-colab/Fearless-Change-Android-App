package de.thkoeln.fherborn.fearlesschange.ui.dashboard.features


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.favorites.FavoritesActivity
import de.thkoeln.fherborn.fearlesschange.ui.overview.OverviewActivity
import de.thkoeln.fherborn.fearlesschange.ui.settings.SettingsActivity
import de.thkoeln.fherborn.fearlesschange.utils.DashboardShowcaseViews
import kotlinx.android.synthetic.main.feature_navigation.*


class NavigationFeature : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.feature_navigation, container, false)

    override fun onStart() {
        super.onStart()
        setListeners()
//        DashboardShowcaseViews(activity!!).startNavigationShowcase()
    }

    private fun setListeners() {
        fab_favorites.setOnClickListener { startActivity(Intent(activity, FavoritesActivity::class.java)) }
        fab_overview.setOnClickListener { startActivity(Intent(activity, OverviewActivity::class.java)) }
        fab_settings.setOnClickListener { startActivity(Intent(activity, SettingsActivity::class.java)) }
    }
}
