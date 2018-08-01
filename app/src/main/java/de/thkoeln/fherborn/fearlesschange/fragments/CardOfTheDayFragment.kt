package de.thkoeln.fherborn.fearlesschange.fragments


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.App
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.views.cardpopup.CardPopup
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_card_of_the_day.*


class CardOfTheDayFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_card_of_the_day, container, false)

    override fun onStart() {
        super.onStart()
        activity?.let {
            //TODO insert logic for card of the day
            (it.application as App).cardDB.cardDao().getById(2).subscribeBy(
                    onNext = { card_of_the_day.card = it },
                    onError = { Snackbar.make(container, it.localizedMessage, Snackbar.LENGTH_LONG) }
            )
        }

        card_of_the_day.onCardClickedListener = { view, card ->
            card?.let { CardPopup(view.context, card).show() }
        }
    }

}
