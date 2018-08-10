package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.NewOnCardActionListener

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardFavoriteBehavior(context: Context?) : NewOnCardActionListener {

    private val cardRepository: CardRepository = CardRepository(context)

    override fun onCardAction(cardView: CardView, card: Card?, action: CardAction) {
        card?.let {
            if (action == CardAction.FAVORITE_CLICKED) {
                it.favorite = !it.favorite
                cardRepository.update(card)
            }
        }
    }
}