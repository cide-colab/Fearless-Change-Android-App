package de.thkoeln.fherborn.fearlesschange.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.db.extensions.loadInBackground
import de.thkoeln.fherborn.fearlesschange.views.cardpopup.CardPopup
import kotlinx.android.synthetic.main.fragment_card_of_the_day.*


class CardOfTheDayFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_card_of_the_day, container, false)

    override fun onStart() {
        super.onStart()

        loadInBackground({it.cardDB.cardDao().getById(2)}) {
            card_of_the_day.card = it
        }

        card_of_the_day.onCardClickedListener = { view, card ->
            card?.let { CardPopup(view.context, card).show() }
        }
    }
}
