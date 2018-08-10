package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.models.Action
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardAction
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardActionRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.OnCardActionListener

/**
 * Created by Florian on 10.08.2018.
 */
open class DefaultCardStatisticBehavior(context: Context?) : OnCardActionListener {

    private val cardActionRepository = CardActionRepository(context)

    override fun onCardClicked(cardView: CardView, card: Card?) {
        card?.let {
            cardActionRepository.insert(
                    CardAction(
                            cardId = card.id,
                            action = Action.CLICK
                    )
            )
        }
    }

}