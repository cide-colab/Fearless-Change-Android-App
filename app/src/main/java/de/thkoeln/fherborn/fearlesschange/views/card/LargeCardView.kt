package de.thkoeln.fherborn.fearlesschange.views.card

import android.content.Context
import android.util.AttributeSet
import de.thkoeln.fherborn.fearlesschange.db.Card
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.R
import kotlinx.android.synthetic.main.layout_card_large.view.*


/**
 * Created by florianherborn on 30.07.18.
 */
class LargeCardView : CardView {

    var onCardClickedListener: ((LargeCardView, Card?) -> Unit)? = null
    var onFavoriteClickedListener: ((LargeCardView, Card?) -> Unit)? = null
    var onNotesClickedListener: ((LargeCardView, Card?) -> Unit)? = null

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onCreateView(inflater: LayoutInflater, context: Context, attributeSet: AttributeSet?) {
        inflater.inflate(R.layout.layout_card_large, this, true)
        onSetListeners()
    }

    private fun onSetListeners() {
        setOnClickListener { view ->
            when(view) {
                card_fav_btn -> onFavoriteClickedListener?.invoke(this, card)
                card_note_btn -> onNotesClickedListener?.invoke(this, card)
                else -> onCardClickedListener?.invoke(this, card)
            }
        }
    }

    override fun onCardChanged(card: Card?) {
        card?.let {
            card_title.text = it.title
            card_problem.text = it.problem
        }
    }

}