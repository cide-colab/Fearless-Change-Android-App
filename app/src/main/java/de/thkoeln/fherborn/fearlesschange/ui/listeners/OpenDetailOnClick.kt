package de.thkoeln.fherborn.fearlesschange.ui.listeners

import android.support.v4.app.FragmentActivity
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.v2.carddetail.CardDetailDialogFragment
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardActionListener
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction

class OpenDetailOnClick(private val activity: FragmentActivity?): CardActionListener {
    override fun onCardAction(card: Card?, action: CardViewAction) {
        when (action) {
            CardViewAction.CARD_CLICKED -> {
                card?.let { openCardDetailPopup(card) }
            }
            else -> {}
        }
    }
    private fun openCardDetailPopup(card: Card) {
        activity?.supportFragmentManager?.let { fm ->
            val cardPopup = CardDetailDialogFragment.newInstance(cardId = card.id)
            cardPopup.show(fm, null)
        }
    }
}