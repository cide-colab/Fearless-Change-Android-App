package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import android.support.v7.app.AppCompatActivity
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.ui.views.CardPopup
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardPreviewBehavior(val activity: AppCompatActivity?) : DefaultCardStatisticBehavior(activity) {

    override fun onCardAction(cardView: CardView, card: Card?, action: CardViewAction) {
        super.onCardAction(cardView, card, action)
        card?.let {
            if (action == CardViewAction.CARD_CLICKED) {
                val popup = CardPopup(activity, card)
                popup.addBehaviors(DefaultCardFavoriteBehavior(activity))
                popup.addBehaviors(DefaultCardNotesBehavior(activity))
                popup.show()
            }
        }
    }

}