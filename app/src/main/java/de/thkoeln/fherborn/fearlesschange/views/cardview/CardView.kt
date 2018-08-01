package de.thkoeln.fherborn.fearlesschange.views.cardview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.db.Card
import kotlinx.android.synthetic.main.layout_card.view.*

/**
 * Created by Florian on 31.07.2018.
 */
abstract class CardView: ConstraintLayout, View.OnClickListener {

    var onCardClickedListener: ((CardView, Card?) -> Unit)? = null

    var card: Card? = null
        set(value) {
            onCardChanged(value)
            field = value
        }

    constructor(context: Context): super(context) { init(context, null) }
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) { init(context, attributeSet) }
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) { init(context, attributeSet) }


    private fun init(context: Context, attributeSet: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView = inflater.inflate(R.layout.layout_card, this, true) as CardView
        card_view.addView(onCreateContentView(inflater, rootView, context, attributeSet))
        card_view.setOnClickListener(this)
        afterContentViewInflated()
    }

    override fun onClick(v: View?) {
        onCardClickedListener?.invoke(this, card)
    }

    protected open fun afterContentViewInflated() {}
    protected abstract fun onCreateContentView(inflater: LayoutInflater, rootView: CardView, context: Context, attributeSet: AttributeSet?): View
    protected abstract fun onCardChanged(card: Card?)

}