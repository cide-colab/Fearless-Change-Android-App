package de.thkoeln.fherborn.fearlesschange.utils

import android.app.Activity
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener
import com.github.amlcurran.showcaseview.targets.ViewTarget
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.R.id.fab_overview

class DashboardShowcaseViews(private val activity: Activity) {

    fun startNavigationShowcase() {
        overviewShowcase()
    }

    private fun getPreparedShowcaseBuilder(viewTargetId: Int, titleId: Int, textId: Int, next: () -> Unit = {}) = ShowcaseView.Builder(activity)
            .setTarget(ViewTarget(viewTargetId, activity))
            .setContentTitle(titleId)
            .setContentText(textId)
            .setStyle(R.style.MyShowCaseView)
            .hideOnTouchOutside()
            .setShowcaseEventListener(object : SimpleShowcaseEventListener() {
                override fun onShowcaseViewDidHide(showcaseView: ShowcaseView?) {
                    next.invoke()
                }

                override fun onShowcaseViewShow(showcaseView: ShowcaseView?) {
                    val appBar = activity.findViewById<AppBarLayout>(R.id.app_bar_layout)
                    appBar.setExpanded(false)
                    val scrollView = activity.findViewById<NestedScrollView>(R.id.dashboard_scrollview)
                    val target = activity.findViewById<View>(viewTargetId)
//                    scrollView.post {scrollView.scrollTo(0, target.y.toInt())}
                    scrollView.requestChildFocus(target, target)
                }
            })

    private fun overviewShowcase() {
        getPreparedShowcaseBuilder(fab_overview, R.string.overview_showcase_title, R.string.overview_showcase_text) { favoriteShowcase() }
                .withMaterialShowcase()
                .build()
    }

    private fun favoriteShowcase() {
        getPreparedShowcaseBuilder(R.id.fab_favorites, R.string.favorites_showcase_title, R.string.favorites_showcase_text) { settingsShowcase() }
                .withMaterialShowcase()
                .build()
    }

    private fun settingsShowcase() {
        getPreparedShowcaseBuilder(R.id.fab_settings, R.string.settings_showcase_title, R.string.settings_showcase_text) { cardOfTheDayShowcase() }
                .withMaterialShowcase()
                .build()
    }

    private fun cardOfTheDayShowcase() {
        getPreparedShowcaseBuilder(R.id.feature_card_of_the_day_container, R.string.card_of_the_day_showcase_title, R.string.card_of_the_day_showcase_text) { randomCardsShowcase() }
                .build()
    }

    private fun randomCardsShowcase() {
        getPreparedShowcaseBuilder(R.id.random_cards_container, R.string.random_card_showcase_title, R.string.random_card_showcase_text) { mostClickedCardShowcase() }
                .build()
    }

    private fun mostClickedCardShowcase() {
        getPreparedShowcaseBuilder(R.id.feature_most_clicked_card_container, R.string.random_card_showcase_title, R.string.random_card_showcase_text)
                .build()
    }
}