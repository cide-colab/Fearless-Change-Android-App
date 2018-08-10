package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.ui.views.cardpopup.CardPopup
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardPreviewBehavior(val context: Context?) : DefaultCardStatisticBehavior(context) {

    override fun onCardAction(cardView: CardView, card: Card?, action: CardAction) {
        super.onCardAction(cardView, card, action)
        card?.let {
            if (action == CardAction.CARD_CLICKED) {
                val popup = CardPopup(context, card)
                popup.addOnCardActionListener(DefaultCardFavoriteBehavior(context))
                popup.addOnCardActionListener(DefaultCardNotesBehavior(context))
                popup.show()
            }
        }
    }

}