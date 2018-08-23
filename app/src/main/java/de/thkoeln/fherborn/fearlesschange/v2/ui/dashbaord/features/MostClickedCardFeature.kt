package de.thkoeln.fherborn.fearlesschange.v2.ui.dashbaord.features


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.Card
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.CardInfo
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedImage
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.CardViewModel
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.CardPreviewAdapter
import kotlinx.android.synthetic.main.feature_most_clicked_card.*


class MostClickedCardFeature : Fragment() {

    private val cardPreviewAdapter = CardPreviewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.feature_most_clicked_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(activity!!).get(CardViewModel::class.java)
        viewModel.getMostClickedCard().observe(this, Observer { onCardUpdate(it) })
        cardPreviewAdapter.onCardClickedListener = { viewModel.cardPreviewClicked(it) }
        card_preview.setAdapter(cardPreviewAdapter)

    }

    private fun onCardUpdate(cardInfo: CardInfo?) {
        cardPreviewAdapter.change(cardInfo)
    }
}
