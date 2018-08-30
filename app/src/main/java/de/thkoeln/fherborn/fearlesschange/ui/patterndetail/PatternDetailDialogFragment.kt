package de.thkoeln.fherborn.fearlesschange.ui.patterndetail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternDetailViewPagerAdapter
import de.thkoeln.fherborn.fearlesschange.ui.notes.PatternNotesFragment
import kotlinx.android.synthetic.main.pattern_detail_dialog.*


/**
 * A DialogFragment to show a flippable pattern detail with its notes
 */
class PatternDetailDialogFragment : DialogFragment() {

    private lateinit var viewModel: PatternViewModel
    private lateinit var pagerAdapter: PatternDetailViewPagerAdapter

    private lateinit var cardIds: LongArray
    private var selectedCardId: Long = -1

    //private val cardBackAdapter = PatternCardBackAdapter()
    //private val cardFrontAdapter = PatternCardFrontAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PatternViewModel::class.java)
        cardIds = arguments?.getLongArray(CARD_ID_KEY)?: LongArray(0)
        selectedCardId = viewModel.extractRequiredId(arguments, SELECTED_CARD_ID_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.pattern_detail_dialog, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_btn.setOnClickListener { dismiss() }
        initCards()
    }

    private fun initCards() {

        pagerAdapter = PatternDetailViewPagerAdapter(fragmentManager = childFragmentManager)
        pattern_detail_viewpager.adapter = pagerAdapter
        pattern_detail_viewpager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                selectedCardId = pagerAdapter.pattern[position].pattern.id
                initNotes()
            }

        })

        viewModel.getPatterns(*cardIds).nonNullObserve(this) {patterns ->
            fragmentManager?.let {
                pagerAdapter.pattern = patterns
                val selected = patterns.find { p -> p.pattern.id == selectedCardId }
                pattern_detail_viewpager.s
                pagerAdapter.notifyDataSetChanged()
            }
        }

        //cardBackAdapter.onFavoriteClickedListener = { viewModel.favoriteButtonClicked(it?.id) }
        //cardFrontAdapter.onFavoriteClickedListener = { viewModel.favoriteButtonClicked(it?.id) }

        //pattern_detail_flippable_card.setCardBackAdapter(cardBackAdapter)
        //pattern_detail_flippable_card.setCardFrontAdapter(cardFrontAdapter)

        //viewModel.getPattern(cardIds).observe(this, Observer { onCardUpdate(it) })
    }

    private fun initNotes() {
        childFragmentManager
                .beginTransaction()
                .replace(R.id.pattern_detail_notes_container, PatternNotesFragment.newInstance(selectedCardId))
                .commit()
    }

    //private fun onCardUpdate(card: PatternInfo?) {
    //    card_detail_notes_count.text = card?.noteCount?.toString()?:0.toString()
    //    cardFrontAdapter.change(card?.pattern)
    //    cardBackAdapter.change(card?.pattern)
    //}

    companion object {
        private const val CARD_ID_KEY = "card_id"
        private const val SELECTED_CARD_ID_KEY = "selected_card_id"
        fun newInstance(patternIds: LongArray, selectedPatternId: Long) =
                PatternDetailDialogFragment().apply {
                    arguments = Bundle().apply {
                        putLongArray(CARD_ID_KEY, patternIds)
                        putLong(SELECTED_CARD_ID_KEY, selectedPatternId)
                    }
                }
    }
}