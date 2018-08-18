package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import de.thkoeln.fherborn.fearlesschange.persistance.models.Card

/**
 * Created by Florian on 10.08.2018.
 */
interface CardViewBehaviorProcessor {

    val cardBehaviors: MutableList<CardViewBehavior>

    fun addBehaviors(vararg newCardBehaviors: CardViewBehavior) =
            cardBehaviors.addAll(newCardBehaviors)

    fun addBehaviors(newCardBehaviors: List<CardViewBehavior>) =
            cardBehaviors.addAll(newCardBehaviors)

    fun removeBehaviors(vararg newCardBehaviors: CardViewBehavior) =
            cardBehaviors.removeAll(newCardBehaviors)

    fun removeBehaviors(newCardBehaviors: List<CardViewBehavior>) =
            cardBehaviors.removeAll(newCardBehaviors)

    fun addDistinctBehaviors(vararg newCardBehaviors: CardViewBehavior) =
            addBehaviors(newCardBehaviors.filter { !cardBehaviors.contains(it) })

    fun addDistinctBehaviors(newCardBehaviors: List<CardViewBehavior>) =
            addBehaviors(newCardBehaviors.filter { !cardBehaviors.contains(it) })

    fun clearBehaviors() =
            cardBehaviors.clear()

    fun performAction(cardView: CardView, card: Card?, action: CardViewAction) =
            cardBehaviors.forEach { it.onCardAction(cardView, card, action) }

}

enum class CardViewAction {
    FAVORITE_CLICKED,
    ADD_NOTE_CLICKED,
    CARD_CLICKED
}

interface CardViewBehavior {
    fun onCardAction(cardView: CardView, card: Card?, action: CardViewAction)
}