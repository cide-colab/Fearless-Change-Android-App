package de.thkoeln.fherborn.fearlesschange.utils

import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener
import com.github.amlcurran.showcaseview.targets.ViewTarget
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.activities.DashboardActivity

class DashboardShowcaseViews {
    companion object {
        fun overviewShowcase(activity: DashboardActivity) {
            val viewTarget = ViewTarget(R.id.fab_overview, activity)
            ShowcaseView.Builder(activity)
                    .setTarget(viewTarget)
                    .setContentTitle("Here you can see an overview of all cards!")
                    .setStyle(R.style.MyShowCaseView)
                    .hideOnTouchOutside()
                    .setShowcaseEventListener(object : SimpleShowcaseEventListener() {
                        override fun onShowcaseViewDidHide(showcaseView: ShowcaseView?) {
                            DashboardShowcaseViews.favoriteShowcase(activity)
                        }
                    })
                    .singleShot(1)
                    .build()
        }

        fun favoriteShowcase(activity: DashboardActivity) {
            val viewTarget = ViewTarget(R.id.fab_favorites, activity)
            ShowcaseView.Builder(activity)
                    .setTarget(viewTarget)
                    .setContentTitle("Here you can see your favorite cards!")
                    .setStyle(R.style.MyShowCaseView)
                    .hideOnTouchOutside()
                    .setShowcaseEventListener(object : SimpleShowcaseEventListener() {
                        override fun onShowcaseViewDidHide(showcaseView: ShowcaseView?) {
                            DashboardShowcaseViews.moreShowcase(activity)
                        }
                    })
                    .build()
        }

        fun moreShowcase(activity: DashboardActivity) {
            val viewTarget = ViewTarget(R.id.fab_more, activity)
            ShowcaseView.Builder(activity)
                    .setTarget(viewTarget)
                    .setContentTitle("Here you can see more cool things!")
                    .setStyle(R.style.MyShowCaseView)
                    .hideOnTouchOutside()
                    .setShowcaseEventListener(object : SimpleShowcaseEventListener() {
                        override fun onShowcaseViewDidHide(showcaseView: ShowcaseView?) {
                            DashboardShowcaseViews.cardOfTheDayShowcase(activity)
                        }
                    })
                    .build()
        }

        fun cardOfTheDayShowcase(activity: DashboardActivity) {
            val viewTarget = ViewTarget(R.id.card_of_the_day, activity)
            ShowcaseView.Builder(activity)
                    .setTarget(viewTarget)
                    .setContentTitle("This is the card of the day!")
                    .hideOnTouchOutside()
                    .setStyle(R.style.MyShowCaseView)
                    .build()
        }
    }
}