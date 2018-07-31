package de.thkoeln.fherborn.fearlesschange.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.db.CardData
import de.thkoeln.fherborn.fearlesschange.views.cardpopup.CardPopup
import kotlinx.android.synthetic.main.fragment_random_cards.*

class RandomCardsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_random_cards, container, false)

    override fun onStart() {
        super.onStart()
        //TODO PLACEHOLDER START ----
        random_cards_1.card = CardData.CARDS[1]
        random_cards_2.card = CardData.CARDS[2]
        random_cards_3.card = CardData.CARDS[5]
        //TODO PLACEHOLDER END ----


        random_cards_1.onCardClickedListener = { view, card ->
            card?.let { CardPopup(view.context, card).show() }
        }
        random_cards_2.onCardClickedListener = { view, card ->
            card?.let { CardPopup(view.context, card).show() }
        }
        random_cards_3.onCardClickedListener = { view, card ->
            card?.let { CardPopup(view.context, card).show() }
        }
    }

}
