package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.support.v7.app.AppCompatActivity
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.ui.views.CardPopupFragment
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardPreviewBehavior(val activity: AppCompatActivity?, private val popupName: String = "CardPopupFragment") : DefaultCardStatisticBehavior(activity) {

    override fun onCardAction(cardView: CardView, card: Card?, action: CardViewAction) {
        super.onCardAction(cardView, card, action)
        card?.let {
            if (action == CardViewAction.CARD_CLICKED) {
                activity?.supportFragmentManager?.let { fm ->
                    val ft = fm.beginTransaction()
                    fm.findFragmentByTag(popupName)?.let {
                        ft.remove(it)
                    }
                    ft.addToBackStack(null)
                    val cardPopup = CardPopupFragment.newInstance(cardId = it.id)
                    cardPopup.addBehaviors(DefaultCardFavoriteBehavior(activity))
                    cardPopup.addBehaviors(DefaultCardNotesBehavior(activity))
                    cardPopup.show(ft, popupName)
                }
            }
        }
    }

}