package de.thkoeln.colab.fearlesschange.ui.dashboard.features


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.ui.favorites.FavoritesActivity
import de.thkoeln.colab.fearlesschange.ui.overview.OverviewActivity
import de.thkoeln.colab.fearlesschange.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.feature_navigation.*


class NavigationFeature : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.feature_navigation, container, false)

    override fun onStart() {
        super.onStart()
        setListeners()
//        DashboardShowcase(activity!!).startNavigationShowcase()
    }

    private fun setListeners() {
        fab_favorites.setOnClickListener { startActivity(Intent(activity, FavoritesActivity::class.java)) }
        fab_overview.setOnClickListener { startActivity(Intent(activity, OverviewActivity::class.java)) }
        fab_settings.setOnClickListener { startActivity(Intent(activity, SettingsActivity::class.java)) }
    }
}
