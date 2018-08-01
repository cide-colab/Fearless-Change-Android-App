package de.thkoeln.fherborn.fearlesschange.fragments


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.App

import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.db.Card
import de.thkoeln.fherborn.fearlesschange.views.cardpopup.CardPopup
import de.thkoeln.fherborn.fearlesschange.views.cardview.CardView
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_random_cards.*

class RandomCardsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_random_cards, container, false)

    override fun onStart() {
        super.onStart()

        loadRandomCards()
        setListeners()

    }

    private fun setListeners() {
        val openPopup: (CardView, Card?) -> Unit = { view, card ->
            card?.let { CardPopup(view.context, card).show() }
        }
        random_cards_1.onCardClickedListener = openPopup
        random_cards_2.onCardClickedListener = openPopup
        random_cards_3.onCardClickedListener = openPopup
        random_cards_reload.setOnClickListener { loadRandomCards() }
    }

    private fun loadRandomCards() {
        activity?.let {
            (it.application as App).cardDB.cardDao().getRandom(3).subscribeBy(
                    onNext = {
                        random_cards_1.card = it[0]
                        random_cards_2.card = it[1]
                        random_cards_3.card = it[2]
                    },
                    onError = { Snackbar.make(container, it.localizedMessage, Snackbar.LENGTH_LONG) }
            )
        }
    }

}
