package de.thkoeln.colab.fearlesschange.ui.dashboard

import androidx.fragment.app.Fragment
import de.thkoeln.colab.fearlesschange.ui.dashboard.features.MostClickedCardFeature
import de.thkoeln.colab.fearlesschange.ui.dashboard.features.NavigationFeature
import de.thkoeln.colab.fearlesschange.ui.dashboard.features.PatternOfTheDayFeature
import de.thkoeln.colab.fearlesschange.ui.dashboard.features.RandomPatternFeature

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
