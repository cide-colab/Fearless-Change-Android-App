package de.thkoeln.fherborn.fearlesschange.views.card

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.db.Card

/**
 * Created by Florian on 31.07.2018.
 */
abstract class CardView: ConstraintLayout {

    var card: Card? = null
        set(value) { onCardChanged(value) }

    constructor(context: Context): super(context) { init(context, null) }
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) { init(context, attributeSet) }
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) { init(context, attributeSet) }


    private fun init(context: Context, attributeSet: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        onCreateView(inflater, context, attributeSet)
    }

    protected abstract fun onCreateView(inflater: LayoutInflater, context: Context, attributeSet: AttributeSet?)
    protected abstract fun onCardChanged(card: Card?)

}