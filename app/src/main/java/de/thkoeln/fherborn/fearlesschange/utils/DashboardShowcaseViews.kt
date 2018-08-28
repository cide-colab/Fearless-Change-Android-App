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
            })

    private fun overviewShowcase() {
        getPreparedShowcaseBuilder(R.id.fab_overview, R.string.overview_showcase_title, R.string.overview_showcase_text) { favoriteShowcase() }
                .singleShot(1)
                .build()
    }

    private fun favoriteShowcase() {
        getPreparedShowcaseBuilder(R.id.fab_favorites, R.string.favorites_showcase_title, R.string.favorites_showcase_text) { settingsShowcase() }
                .build()
    }

    private fun settingsShowcase() {
        getPreparedShowcaseBuilder(R.id.fab_more, R.string.settings_showcase_title, R.string.settings_showcase_text) { cardOfTheDayShowcase() }
                .build()
    }

    private fun cardOfTheDayShowcase() {
        getPreparedShowcaseBuilder(R.id.card_of_the_day, R.string.card_of_the_day_showcase_title, R.string.card_of_the_day_showcase_text) {randomCardsShowcase()}
                .build()
    }

    private fun randomCardsShowcase() {
        getPreparedShowcaseBuilder(R.id.random_cards_2, R.string.random_card_showcase_title, R.string.random_card_showcase_text)
                .build()
    }
}