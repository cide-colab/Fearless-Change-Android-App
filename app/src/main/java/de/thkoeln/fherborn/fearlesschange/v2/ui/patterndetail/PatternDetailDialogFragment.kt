package de.thkoeln.fherborn.fearlesschange.v2.ui.patterndetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.Pattern
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedBackground
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.PatternCardBackAdapter
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.PatternCardFrontAdapter
import de.thkoeln.fherborn.fearlesschange.v2.ui.notes.PatternNotesFragment
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
        setBackgrounds()
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

    private fun onCardUpdate(card: Pattern?) {

        cardFrontAdapter.change(card)
        cardBackAdapter.change(card)
    }

    private fun setBackgrounds() {
        pattern_detail_dialog_notes_header.setOptimizedBackground(R.drawable.notes_header_bg)
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