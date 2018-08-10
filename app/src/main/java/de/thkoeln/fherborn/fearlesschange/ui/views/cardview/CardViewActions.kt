package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import de.thkoeln.fherborn.fearlesschange.persistance.models.Card

/**
 * Created by Florian on 10.08.2018.
 */
interface NewCardViewActions {

    val onCardActionListeners: MutableList<NewOnCardActionListener>

    fun addOnCardActionListener(vararg onCardActionListener: NewOnCardActionListener) {
        onCardActionListeners.addAll(onCardActionListener)
    }
    fun addOnCardActionListener(onCardActionListener: List<NewOnCardActionListener>) {
        onCardActionListeners.addAll(onCardActionListener)
    }
    fun removeOnCardActionListener(vararg onCardActionListener: NewOnCardActionListener) {
        onCardActionListeners.removeAll(onCardActionListener)
    }

    fun performAction(cardView: CardView, card: Card?, action: CardAction) {
        onCardActionListeners.forEach{it.onCardAction(cardView, card, action)}
    }
}

enum class CardAction {
    FAVORITE_CLICKED,
    NOTES_CLICKED,
    CARD_CLICKED
}

interface NewOnCardActionListener {
    fun onCardAction(cardView: CardView, card: Card?, action: CardAction)
}