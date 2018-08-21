package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatisticAction
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardStatisticRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors.DefaultCardPreviewBehavior
import kotlinx.android.synthetic.main.fragment_most_clicked_card.*


class MostClickedCardFragment : Fragment() {

    private lateinit var cardRepository: CardRepository
    private lateinit var cardActionRepository: CardStatisticRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_most_clicked_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardRepository = CardRepository(activity?.application)
        cardActionRepository = CardStatisticRepository(activity?.application)

        cardActionRepository.getCardByMostActionWithNoteCount(CardStatisticAction.CLICK).observe( this, Observer {
            it?.let {
                most_clicked_card.card = it.card
                most_clicked_card.notesCount = it.noteCount
            }
        })

        most_clicked_card.addCardActionListener(DefaultCardPreviewBehavior(activity as AppCompatActivity))
    }
}
