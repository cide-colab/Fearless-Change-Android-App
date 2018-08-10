package de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.OnCardActionListener

/**
 * Created by Florian on 10.08.2018.
 */
class DefaultCardFavoriteBehavior(context: Context?) : OnCardActionListener {

    private val cardRepository: CardRepository = CardRepository(context)

    override fun onFavoriteClickedListener(cardView: CardView, card: Card?) {
        card?.let {
            it.favorite = !it.favorite
            cardRepository.update(card)
        }
    }

    override fun onNotesClickedListener(cardView: CardView, card: Card?) {
        super.onNotesClickedListener(cardView, card)
    }
}