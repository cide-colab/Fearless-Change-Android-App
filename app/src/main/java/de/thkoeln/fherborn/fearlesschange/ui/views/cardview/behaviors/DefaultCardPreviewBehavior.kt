package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.support.v7.app.AppCompatActivity
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.v2.carddetail.CardDetailDialogFragment
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardPreviewBehavior(val activity: AppCompatActivity?, private val popupName: String = "CardDetailDialogFragment") : DefaultCardStatisticBehavior(activity) {

    override fun onCardAction(card: Card?, action: CardViewAction) {
        super.onCardAction(card, action)
        card?.let {
            if (action == CardViewAction.CARD_CLICKED) {
                activity?.supportFragmentManager?.let { fm ->
                    val ft = fm.beginTransaction()
                    fm.findFragmentByTag(popupName)?.let {
                        ft.remove(it)
                    }
                    ft.addToBackStack(null)
                    val cardPopup = CardDetailDialogFragment.newInstance(cardId = it.id)
                    cardPopup.addCardActionListener(DefaultCardFavoriteBehavior(activity))
                    cardPopup.addCardActionListener(DefaultCardNotesBehavior(activity))
                    cardPopup.show(ft, popupName)
                }
            }
        }
    }

}