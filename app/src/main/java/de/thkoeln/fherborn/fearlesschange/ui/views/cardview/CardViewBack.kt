package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import android.content.Context
import android.util.AttributeSet
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.R
import kotlinx.android.synthetic.main.layout_card_view_back.view.*


/**
 * Created by florianherborn on 30.07.18.
 */
class CardViewBack : CardView {

    var onFavoriteClickedListener: ((CardViewBack, Card?) -> Unit)? = null
    var onNotesClickedListener: ((CardViewBack, Card?) -> Unit)? = null

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onCreateContentView(inflater: LayoutInflater, rootView: CardView, context: Context, attributeSet: AttributeSet?)
            = inflater.inflate(R.layout.layout_card_view_back, rootView, false)

    override fun afterContentViewInflated() {
        card_fav_btn.setOnClickListener{onFavoriteClickedListener?.invoke(this, card)}
        card_note_btn.setOnClickListener{onNotesClickedListener?.invoke(this, card)}
    }

    override fun onCardChanged(card: Card?) {
        card?.let {
            card_title.text = it.title
            card_solution.text = it.solution
            card_buts.text = it.buts
        }
    }

}