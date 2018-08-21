package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.listeners.OpenDetailOnClick
import de.thkoeln.fherborn.fearlesschange.ui.viewmodels.CardViewModel
import kotlinx.android.synthetic.main.fragment_card_of_the_day.*


class CardOfTheDayFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_card_of_the_day, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)

        viewModel.getCardOfTheDay().observe(this, Observer { cardWithNodeCount ->
            card_of_the_day.card = cardWithNodeCount?.card
            card_of_the_day.notesCount = cardWithNodeCount?.noteCount ?: 0
        })

        card_of_the_day.addDistinctCardActionListener(OpenDetailOnClick(activity))
    }
}


