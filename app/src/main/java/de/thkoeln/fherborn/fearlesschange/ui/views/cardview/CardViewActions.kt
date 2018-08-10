package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import de.thkoeln.fherborn.fearlesschange.persistance.models.Card

/**
 * Created by Florian on 10.08.2018.
 */
interface CardViewActions: OnCardActionListener {

    val onCardActionListeners: MutableList<OnCardActionListener>

    fun addOnCardActionListener(vararg onCardActionListener: OnCardActionListener) {
        onCardActionListeners.addAll(onCardActionListener)
    }
    fun addOnCardActionListener(onCardActionListener: List<out OnCardActionListener>) {
        onCardActionListeners.addAll(onCardActionListener)
    }
    fun removeOnCardActionListener(vararg onCardActionListener: OnCardActionListener) {
        onCardActionListeners.removeAll(onCardActionListener)
    }

    fun performCardClick(cardView: CardView, card: Card?) {
        onCardActionListeners.forEach{it.onCardClicked(cardView, card)}
    }

    fun performFavoriteClick(cardView: CardView, card: Card?) {
        onCardActionListeners.forEach{it.onFavoriteClickedListener(cardView, card)}
    }

    fun performNotesClick(cardView: CardView, card: Card?) {
        onCardActionListeners.forEach{it.onNotesClickedListener(cardView, card)}
    }

}

interface OnCardActionListener: CardView.OnCardClickedListener {
    fun onFavoriteClickedListener(cardView: CardView, card: Card?) {}
    fun onNotesClickedListener(cardView: CardView, card: Card?) {}
}