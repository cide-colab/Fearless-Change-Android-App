package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors.DefaultCardPreviewBehavior
import kotlinx.android.synthetic.main.fragment_card_of_the_day.*


class CardOfTheDayFragment : Fragment() {

    private lateinit var cardRepository: CardRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_card_of_the_day, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardRepository = CardRepository(activity?.application)

        cardRepository.getCount().observe(this, Observer {
            calculateCardOfTheDay(it)?.let {
                cardRepository.getElementWithIndex(it).observe(this, Observer {
                    card_of_the_day.card = it
                })
            }
        })

        card_of_the_day.addBehaviors(DefaultCardPreviewBehavior(activity as AppCompatActivity))
    }

    private fun calculateCardOfTheDay(countOfCards: Long?): Long? {
        val currentDay = System.currentTimeMillis()/1000/60/60/24
        return when (countOfCards) {
            null, 0L -> null
            else -> currentDay % countOfCards
        }
    }
}
