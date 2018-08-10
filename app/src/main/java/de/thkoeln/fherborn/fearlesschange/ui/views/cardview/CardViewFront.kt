package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import android.content.Context
import android.util.AttributeSet
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.getResourceId
import kotlinx.android.synthetic.main.layout_card_view_front.view.*


/**
 * Created by florianherborn on 30.07.18.
 */
class CardViewFront : CardView, CardViewActions {

    override val onCardActionListeners = mutableListOf<OnCardActionListener>()

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onCreateContentView(inflater: LayoutInflater, rootView: CardView, context: Context, attributeSet: AttributeSet?)
            = inflater.inflate(R.layout.layout_card_view_front, rootView, false)

    override fun afterContentViewInflated() {
        addOnCardClickedListener{cardView, card ->  performCardClick(cardView, card)}
        card_fav_btn.setOnClickListener{
            performFavoriteClick(this, card)
            onCardChanged(card)
        }
        card_note_btn.setOnClickListener{performNotesClick(this, card)}
    }

    override fun onCardChanged(card: Card?) {
        card?.let {
            card_fav_btn.setImageResource(
                    if(it.favorite) R.drawable.ic_favorite_full_white_24dp
                    else R.drawable.ic_favorite_white
            )
            card_title.text = it.title
            card_problem.text = it.problem
            card_image.setImageResource(
                    getResourceId(context, card.pictureName, "drawable", context.packageName)
            ?:R.drawable.img_placeholder)
        }
    }

}