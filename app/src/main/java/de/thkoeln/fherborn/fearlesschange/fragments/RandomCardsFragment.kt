package de.thkoeln.fherborn.fearlesschange.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.databases.models.Card
import de.thkoeln.fherborn.fearlesschange.databases.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.views.cardpopup.CardPopup
import de.thkoeln.fherborn.fearlesschange.views.cardview.CardView
import kotlinx.android.synthetic.main.fragment_random_cards.*

class RandomCardsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_random_cards, container, false)

    private lateinit var cardRepository: CardRepository

    override fun onStart() {
        super.onStart()

        cardRepository = CardRepository(activity?.application)

        reload()
        setListeners()

    }

    private fun setListeners() {
        setCardListener(random_cards_1, random_cards_2, random_cards_3)
        random_cards_reload.setOnClickListener { reload() }
    }

    fun reload() {
        loadRandomCardsToView(random_cards_1, random_cards_2, random_cards_3)
    }

    private fun setCardListener(vararg cardViews: CardView) {
        cardViews.forEach {
            it.onCardClickedListener = { view, card ->
                card?.let {
                    CardPopup(view.context, card).show()
                }
            }
        }
    }

    private fun loadRandomCardsToView(vararg cardViews: CardView) {
        cardRepository.getRandom(cardViews.size).observe(this, Observer {
            it?.forEachIndexed{index, card -> cardViews[index].card = card}
        })
    }
}
