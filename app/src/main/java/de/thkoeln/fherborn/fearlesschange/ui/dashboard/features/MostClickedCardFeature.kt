package de.thkoeln.fherborn.fearlesschange.ui.dashboard.features


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternCardPreviewAdapter
import kotlinx.android.synthetic.main.feature_most_clicked_card.*


class MostClickedCardFeature : Fragment() {

    private lateinit var cardPreviewAdapter: PatternCardPreviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.feature_most_clicked_card, container, false)

    override fun onResume() {
        super.onResume()

        val viewModel = ViewModelProviders.of(activity!!).get(PatternViewModel::class.java)
        viewModel.mostClickedPattern.observe(this, Observer { onCardUpdate(it) })
        cardPreviewAdapter = PatternCardPreviewAdapter()
        cardPreviewAdapter.onCardClickedListener = { viewModel.cardPreviewClicked(it) }
        card_preview.setAdapter(cardPreviewAdapter)
    }

    private fun onCardUpdate(patternInfo: PatternInfo?) {
        cardPreviewAdapter.change(patternInfo)
    }
}
