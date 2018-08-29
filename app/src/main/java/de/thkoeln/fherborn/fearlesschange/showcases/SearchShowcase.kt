package de.thkoeln.fherborn.fearlesschange.showcases

import android.app.Activity
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig


class SearchShowcase(activity: Activity): MaterialShowcaseSequence(activity, SHOWCASE_NAME) {

    init {
        val config = ShowcaseConfig()
        config.delay = 200

        setConfig(config)


        addSequenceItem(
                MaterialShowcaseView.Builder(activity)
                        .setTarget(activity.findViewById(R.id.search_keyword))
                        .setTitleText(R.string.search_input_showcase_title)
                        .setContentText(R.string.search_input_showcase_text)
                        .setDismissText(R.string.showcase_btn_next_label)
                        .withRectangleShape()
                        .build()
        )

        addSequenceItem(
                MaterialShowcaseView.Builder(activity)
                        .setTarget(activity.findViewById(R.id.search_button))
                        .setTitleText(R.string.search_button_showcase_title)
                        .setContentText(R.string.search_button_showcase_text)
                        .setDismissText(R.string.showcase_btn_finish_label)
                        .build()
        )

    }

    companion object {
        const val SHOWCASE_NAME = "search_showcase"
    }
}