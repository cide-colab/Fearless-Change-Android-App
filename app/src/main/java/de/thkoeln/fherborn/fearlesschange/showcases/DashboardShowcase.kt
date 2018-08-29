package de.thkoeln.fherborn.fearlesschange.showcases

import android.app.Activity
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig


class DashboardShowcase(activity: Activity): MaterialShowcaseSequence(activity, SHOWCASE_NAME) {

    init {

        val config = ShowcaseConfig()
        config.delay = 200

        setConfig(config)

        addSequenceItem(
                activity.findViewById<View>(R.id.fab_overview),
                activity.resources.getString(R.string.overview_showcase_title),
                activity.resources.getString(R.string.overview_showcase_text),
                activity.resources.getString(R.string.showcase_btn_next_label)
        )

        addSequenceItem(
                activity.findViewById<View>(R.id.fab_favorites),
                activity.resources.getString(R.string.favorites_showcase_title),
                activity.resources.getString(R.string.favorites_showcase_text),
                activity.resources.getString(R.string.showcase_btn_next_label)
        )

        addSequenceItem(
                activity.findViewById<View>(R.id.fab_settings),
                activity.resources.getString(R.string.settings_showcase_title),
                activity.resources.getString(R.string.settings_showcase_text),
                activity.resources.getString(R.string.showcase_btn_next_label)
        )

        addSequenceItem(
                MaterialShowcaseView.Builder(activity)
                        .setTarget(activity.findViewById(R.id.card_preview))
                        .setTitleText(R.string.card_showcase_title)
                        .setContentText(R.string.card_of_the_day_showcase_text)
                        .setDismissText(R.string.showcase_btn_finish_label)
                        .withRectangleShape()
                        .build()
        )
    }

    companion object {
        const val SHOWCASE_NAME = "dashboard_showcase"
    }
}