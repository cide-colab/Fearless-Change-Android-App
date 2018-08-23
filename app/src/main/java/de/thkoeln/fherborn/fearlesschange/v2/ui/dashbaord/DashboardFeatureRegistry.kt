package de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord

import android.support.v4.app.Fragment
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.CardOfTheDayFeature
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.MostClickedCardFeature
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.NavigationFeature
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.RandomCardsFeature

/**
 * Created by florianherborn on 22.08.18.
 */
object DashboardFeatureRegistry {
    val dashboardFeatures: List<Fragment> by lazy {
        listOf(
                NavigationFeature(),
                CardOfTheDayFeature(),
                RandomCardsFeature(),
                MostClickedCardFeature()
        )
    }
}
