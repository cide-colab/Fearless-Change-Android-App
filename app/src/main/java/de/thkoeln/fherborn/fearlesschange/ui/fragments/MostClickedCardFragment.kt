package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.Action
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardAction
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardActionRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardpopup.CardPopup
import kotlinx.android.synthetic.main.fragment_most_clicked_card.*


class MostClickedCardFragment : Fragment() {

    private lateinit var cardRepository: CardRepository
    private lateinit var cardActionRepository: CardActionRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_most_clicked_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardRepository = CardRepository(activity?.application)
        cardActionRepository = CardActionRepository(activity?.application)

        cardActionRepository.getMostByAction(Action.CLICK).observe( this, Observer {
            it?.let {
                cardRepository.getById(it.cardId).observe(this, Observer {
                    most_clicked_card.card = it
                })
            }
        })

        most_clicked_card.onCardClickedListener = { view, card ->
            card?.let {
                cardActionRepository.insert(
                        CardAction(cardId = card.id, action = Action.CLICK)
                )
                CardPopup(view.context, card).show()
            }
        }
    }
}
