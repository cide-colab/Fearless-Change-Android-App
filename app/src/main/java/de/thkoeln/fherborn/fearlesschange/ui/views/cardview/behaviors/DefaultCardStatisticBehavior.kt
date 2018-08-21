package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatisticAction
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatistic
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardStatisticRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardActionListener

/**
 * Created by Florian on 10.08.2018.
 */
open class DefaultCardStatisticBehavior(context: Context?) : CardActionListener {

    private val cardActionRepository = CardStatisticRepository(context)

    override fun onCardAction(card: Card?, action: CardViewAction) {
        card?.let {
            if (action == CardViewAction.CARD_CLICKED) {
                cardActionRepository.insert(
                        CardStatistic(
                                cardId = card.id,
                                action = CardStatisticAction.CLICK
                        )
                )
            }
        }

    }

}