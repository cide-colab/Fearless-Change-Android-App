package de.thkoeln.fherborn.fearlesschange.ui.patterndetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.Pattern
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternCardBackAdapter
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternCardFrontAdapter
import de.thkoeln.fherborn.fearlesschange.ui.customs.card.PatternCardDelegation
import de.thkoeln.fherborn.fearlesschange.ui.notes.PatternNotesFragment
import kotlinx.android.synthetic.main.pattern_detail_dialog.*


/**
 * A DialogFragment to show a flippable pattern detail with its notes
 */
class PatternDetailDialogFragment : DialogFragment() {

    private lateinit var viewModel: PatternViewModel
    private var cardId: Long = -1L

    private val cardBackAdapter = PatternCardBackAdapter()
    private val cardFrontAdapter = PatternCardFrontAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)
        cardId = viewModel.extractRequiredId(arguments, CARD_ID_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.pattern_detail_dialog, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_btn.setOnClickListener { dismiss() }
        initCards()
        initNotes()
    }

    private fun initNotes() {
        childFragmentManager
                .beginTransaction()
                .replace(R.id.pattern_detail_notes_container, PatternNotesFragment.newInstance(cardId))
                .commit()
    }

    private fun initCards() {

        cardBackAdapter.onFavoriteClickedListener = { viewModel.favoriteButtonClicked(it?.id) }
        cardFrontAdapter.onFavoriteClickedListener = { viewModel.favoriteButtonClicked(it?.id) }

        pattern_detail_flippable_card.setCardBackAdapter(cardBackAdapter)
        pattern_detail_flippable_card.setCardFrontAdapter(cardFrontAdapter)

        viewModel.getPattern(cardId).observe(this, Observer { onCardUpdate(it) })
    }

    private fun onCardUpdate(card: PatternInfo?) {
        card_detail_notes_count.text = card?.noteCount?.toString()?:0.toString()
        cardFrontAdapter.change(card?.pattern)
        cardBackAdapter.change(card?.pattern)
    }

    companion object {
        private const val CARD_ID_KEY = "card_id"
        fun newInstance(cardId: Long) =
                PatternDetailDialogFragment().apply {
                    arguments = Bundle().apply {
                        putLong(CARD_ID_KEY, cardId)
                    }
                }
    }
}