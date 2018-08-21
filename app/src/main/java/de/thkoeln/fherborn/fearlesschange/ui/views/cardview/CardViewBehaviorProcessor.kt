package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import de.thkoeln.fherborn.fearlesschange.persistance.models.Card

/**
 *
 * BehaviorProcessor is used to perform actions on an cardView. If some Behaviors for a cardview can be added to the Class,
 * the class should implement the CardViewBehaviorProcessor
 * This class provides all functions to add Behaviors and perform actions with this behaviors
 *
 * @author Florian Herborn on 10.08.2018.
 * @since 0.0.1
 * @property cardBehaviors Behave what should be accepted by the cardView/s
 */
interface CardViewBehaviorProcessor {

    val cardBehaviors: MutableList<CardActionListener>

    /**
     * Add behaviors to the cardView
     * @param cardBehaviors that should be added
     */
    fun addCardActionListener(vararg cardBehaviors: CardActionListener) =
            this.cardBehaviors.addAll(cardBehaviors)

    /**
     * Add behaviors to the cardView
     * @param cardBehaviors that should be added
     */
    fun addCardActionListener(cardBehaviors: List<CardActionListener>) =
            this.cardBehaviors.addAll(cardBehaviors)

    /**
     * Removes behaviors from the cardView
     * @param cardBehaviors that should be removed
     */
    fun removeBehaviors(vararg cardBehaviors: CardActionListener) =
            this.cardBehaviors.removeAll(cardBehaviors)

    /**
     * Removes behaviors from the cardView
     * @param cardBehaviors that should be removed
     */
    fun removeBehaviors(cardBehaviors: List<CardActionListener>) =
            this.cardBehaviors.removeAll(cardBehaviors)

    /**
     * Add behaviors to the cardView if it doesn't exist
     * @param cardBehaviors that should be added
     */
    fun addDistinctCardActionListener(vararg cardBehaviors: CardActionListener) =
            this.addCardActionListener(cardBehaviors.filter { !this.cardBehaviors.contains(it) })
    /**
     * Add behaviors to the cardView if it doesn't exist
     * @param cardBehaviors that should be added
     */
    fun addDistinctCardActionListener(listener: (card: Card?, action: CardViewAction) -> Unit) =
            this.addCardActionListener(cardBehaviors.filter { !this.cardBehaviors.contains(it) })

    /**
     * Add behaviors to the cardView if it doesn't exist
     * @param cardBehaviors that should be added
     */
    fun addDistinctCardActionListener(cardBehaviors: List<CardActionListener>) =
            this.addCardActionListener(cardBehaviors.filter { !this.cardBehaviors.contains(it) })

    /**
     * Removes all behaviors from cardView
     */
    fun clearBehaviors() =
            cardBehaviors.clear()

    /**
     * Invokes the behaviors that are added on the cardView
     */
    fun performAction(card: Card?, action: CardViewAction) =
            cardBehaviors.forEach { it.onCardAction(card, action) }

}

/**
 * Actions that can be performed form the cardviews
 */
enum class CardViewAction {
    FAVORITE_CLICKED,
    CARD_CLICKED
}

/**
 * Behavior that can be implemented to add it to the cardView
 */
interface CardActionListener {
    fun onCardAction(card: Card?, action: CardViewAction)
}
