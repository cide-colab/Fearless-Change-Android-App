package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardActionListener

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardFavoriteBehavior(context: Context?) : CardActionListener {

    private val cardRepository: CardRepository = CardRepository(context)

    override fun onCardAction(card: Card?, action: CardViewAction) {
        card?.let {
            if (action == CardViewAction.FAVORITE_CLICKED) {
                it.favorite = !it.favorite
                cardRepository.update(card)
            }
        }
    }
}