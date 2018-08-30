package de.thkoeln.fherborn.fearlesschange.ui.patterndetail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternCardBackAdapter
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternCardFrontAdapter
import kotlinx.android.synthetic.main.fragment_flippable_card.*

class FlippableCardFragment : Fragment() {

    private lateinit var frontAdapter: PatternCardFrontAdapter
    private lateinit var backAdapter: PatternCardBackAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  =
            inflater.inflate(R.layout.fragment_flippable_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(activity!!).get(PatternViewModel::class.java)
        viewModel.getPattern(arguments?.getLong(PATTERN_ID_KEY)).nonNullObserve(this) {
            updatePattern(it)
        }

        frontAdapter = PatternCardFrontAdapter()
        backAdapter = PatternCardBackAdapter()
        frontAdapter.onFavoriteClickedListener = { viewModel.favoriteButtonClicked(it?.id) }
        backAdapter.onFavoriteClickedListener = { viewModel.favoriteButtonClicked(it?.id) }
        flippable_card.setCardBackAdapter(backAdapter)
        flippable_card.setCardFrontAdapter(frontAdapter)
    }

    private fun updatePattern(it: PatternInfo) {
        frontAdapter.change(it.pattern)
        backAdapter.change(it.pattern)
    }

    companion object {
        private const val PATTERN_ID_KEY = "pattern_id"
        fun newInstance(patternId: Long) =
                FlippableCardFragment().apply {
                    arguments = Bundle().apply {
                        putLong(PATTERN_ID_KEY, patternId)
                    }
                }
    }
}
