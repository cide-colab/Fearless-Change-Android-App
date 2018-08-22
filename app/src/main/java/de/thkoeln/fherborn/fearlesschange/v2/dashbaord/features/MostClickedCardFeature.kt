package de.thkoeln.fherborn.fearlesschange.v2.dashbaord.features


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.v2.extensions.setOptimizedImage
import de.thkoeln.fherborn.fearlesschange.v2.viewmodel.CardViewModel
import kotlinx.android.synthetic.main.card_preview.*


class MostClickedCardFeature : Fragment() {

    private lateinit var viewModel: CardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.feature_most_clicked_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(CardViewModel::class.java)
        viewModel.getMostClickedCard().observe(this, Observer { onCardUpdate(it) })

    }

    private fun onCardUpdate(card: Card?) {
        card_preview_card_title.text = card?.title
        card_preview_card_summary.text = card?.summary
        card_preview_card_image.setOptimizedImage(card?.pictureName, R.drawable.default_pattern_image)
        card_preview_card.setOnClickListener { viewModel.cardClicked(card) }
        card_preview_favorite_icon.setImageResource(when(card?.favorite) {
            true -> R.drawable.ic_favorite_full_white
            else -> R.drawable.ic_favorite_white
        })
    }
}