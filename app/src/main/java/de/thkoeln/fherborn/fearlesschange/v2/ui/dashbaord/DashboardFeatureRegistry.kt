package de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord

import android.support.v4.app.Fragment
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.PatternOfTheDayFeature
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.MostClickedCardFeature
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.NavigationFeature
import de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features.RandomPatternFeature

/**
 * Created by florianherborn on 22.08.18.
 */
object DashboardFeatureRegistry {
    val dashboardFeatures: List<Fragment> by lazy {
        listOf(
                NavigationFeature(),
                PatternOfTheDayFeature(),
                RandomPatternFeature(),
                MostClickedCardFeature()
        )
    }
}
