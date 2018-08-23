package de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord

import android.support.v4.app.Fragment
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.CardOfTheDayFeature
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.MostClickedCardFeature
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.NavigationFeature

/**
 * Created by florianherborn on 22.08.18.
 */
object DashbaordFeatureRegistry {
    val dashboardFeatures: List<Fragment> by lazy {
        listOf(
                NavigationFeature(),
                CardOfTheDayFeature(),
                MostClickedCardFeature()
        )
    }
}
