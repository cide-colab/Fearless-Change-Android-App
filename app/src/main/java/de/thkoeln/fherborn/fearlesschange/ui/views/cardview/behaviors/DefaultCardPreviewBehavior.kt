package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import android.util.Log
import de.thkoeln.fherborn.fearlesschange.persistance.models.Action
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardAction
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardActionRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardpopup.CardPopup
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.OnCardActionListener

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardPreviewBehavior(val context: Context?) : DefaultCardStatisticBehavior(context) {

    override fun onCardClicked(cardView: CardView, card: Card?) {
        super.onCardClicked(cardView, card)
        card?.let {
            val popup = CardPopup(context, card)
            popup.addOnCardActionListener(DefaultCardFavoriteBehavior(context))
            popup.addOnCardActionListener(DefaultCardNotesBehavior(context))
            popup.show()
        }
    }

}