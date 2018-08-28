package de.thkoeln.fherborn.fearlesschange.utils

import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener
import com.github.amlcurran.showcaseview.targets.ViewTarget
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.activities.DashboardActivity

class DashboardShowcaseViews(private val activity: DashboardActivity) {

    fun startShowcase() {
        overviewShowcase()
    }

    private fun getPreparedShowcaseBuilder(viewTargetId: Int, titleId: Int, next: SimpleShowcaseEventListener) = ShowcaseView.Builder(activity)
            .setTarget(ViewTarget(viewTargetId, activity))
            .setContentTitle(titleId)
            .setStyle(R.style.MyShowCaseView)
            .hideOnTouchOutside()
            .setShowcaseEventListener(next)

    private fun overviewShowcase() {
        getPreparedShowcaseBuilder(R.id.fab_overview, R.string.overview_title, object : SimpleShowcaseEventListener() {
                    override fun onShowcaseViewDidHide(showcaseView: ShowcaseView?) {
                        favoriteShowcase()
                    }
                })
                .singleShot(1)
                .build()
    }

    private fun favoriteShowcase() {
        val viewTarget = ViewTarget(R.id.fab_favorites, activity)
        ShowcaseView.Builder(activity)
                .setTarget(viewTarget)
                .setContentTitle("Here you can see your favorite cards!")
                .setStyle(R.style.MyShowCaseView)
                .hideOnTouchOutside()
                .setShowcaseEventListener(object : SimpleShowcaseEventListener() {
                    override fun onShowcaseViewDidHide(showcaseView: ShowcaseView?) {
                        moreShowcase()
                    }
                })
                .build()
    }

    private fun moreShowcase() {
        val viewTarget = ViewTarget(R.id.fab_more, activity)
        ShowcaseView.Builder(activity)
                .setTarget(viewTarget)
                .setContentTitle("Here you can see more cool things!")
                .setStyle(R.style.MyShowCaseView)
                .hideOnTouchOutside()
                .setShowcaseEventListener(object : SimpleShowcaseEventListener() {
                    override fun onShowcaseViewDidHide(showcaseView: ShowcaseView?) {
                        cardOfTheDayShowcase()
                    }
                })
                .build()
    }

    private fun cardOfTheDayShowcase() {
        val viewTarget = ViewTarget(R.id.card_of_the_day, activity)
        ShowcaseView.Builder(activity)
                .setTarget(viewTarget)
                .setContentTitle("This is the card of the day!")
                .hideOnTouchOutside()
                .setStyle(R.style.MyShowCaseView)
                .build()
    }
}