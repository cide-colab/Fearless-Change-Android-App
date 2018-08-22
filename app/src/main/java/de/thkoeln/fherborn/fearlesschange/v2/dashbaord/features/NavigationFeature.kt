package de.thkoeln.fherborn.fearlesschange.v2.dashbaord.features


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.activities.FavoritesActivity
import de.thkoeln.fherborn.fearlesschange.ui.activities.MoreActivity
import de.thkoeln.fherborn.fearlesschange.ui.activities.OverviewActivity
import kotlinx.android.synthetic.main.feature_navigation.*


class NavigationFeature : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.feature_navigation, container, false)

    override fun onStart() {
        super.onStart()
        setListeners()
    }

    private fun setListeners() {
        fab_favorites.setOnClickListener{ startActivity(Intent(activity, FavoritesActivity::class.java))}
        fab_more.setOnClickListener{ startActivity(Intent(activity, MoreActivity::class.java))}
        fab_overview.setOnClickListener{ startActivity(Intent(activity, OverviewActivity::class.java))}
    }
}
