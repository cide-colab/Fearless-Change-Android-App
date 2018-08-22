package de.thkoeln.fherborn.fearlesschange.v2.dashbaord

import android.support.v4.app.Fragment
import de.thkoeln.fherborn.fearlesschange.v2.dashbaord.features.CardOfTheDayFeature
import de.thkoeln.fherborn.fearlesschange.v2.dashbaord.features.MostClickedCardFeature
import de.thkoeln.fherborn.fearlesschange.v2.dashbaord.features.NavigationFeature

/**
 * Created by florianherborn on 22.08.18.
 */
val dashboardFeatures: List<Fragment> by lazy {
    listOf(
            NavigationFeature(),
            CardOfTheDayFeature(),
            MostClickedCardFeature()
    )
}