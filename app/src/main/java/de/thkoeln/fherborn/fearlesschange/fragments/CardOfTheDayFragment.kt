package de.thkoeln.fherborn.fearlesschange.fragments


import android.app.Application
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.databases.CardDatabase
import de.thkoeln.fherborn.fearlesschange.databases.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.views.cardpopup.CardPopup
import kotlinx.android.synthetic.main.fragment_card_of_the_day.*


class CardOfTheDayFragment : Fragment() {

    private lateinit var cardRepository: CardRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_card_of_the_day, container, false)

    override fun onStart() {
        super.onStart()

        cardRepository = CardRepository(activity?.application)

        cardRepository.getCount().observe(this, Observer {
            cardRepository.getElementWithIndex(calculateCardOfTheDay(it)).observe(this, Observer {
                card_of_the_day.card = it
            })
        })

        card_of_the_day.onCardClickedListener = { view, card ->
            card?.let { CardPopup(view.context, card).show() }
        }
    }

    private fun calculateCardOfTheDay(countOfCards: Long?): Long {
        val currentDay = System.currentTimeMillis()/1000/60/60/24
        return currentDay % (countOfCards?:0) -1
    }
}
