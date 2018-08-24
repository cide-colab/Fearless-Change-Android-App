package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.Pattern
import kotlinx.android.synthetic.main.layout_card.view.*

/**
 * Created by Florian on 31.07.2018.
 */
abstract class CardView: ConstraintLayout, CardViewBehaviorProcessor {

    override val cardBehaviors = mutableListOf<CardActionListener>()

    var card: Pattern? = null
        set(value) {
            //onCardChanged(message)
            field = value
        }

    constructor(context: Context): super(context) { init(context, null) }
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) { init(context, attributeSet) }
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) { init(context, attributeSet) }


    open fun init(context: Context, attributeSet: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView = inflater.inflate(R.layout.layout_card, this, true) as CardView
        card_front_card.addView(onCreateContentView(inflater, rootView, context, attributeSet))
        card_front_card.setOnClickListener{ performAction(card, CardViewAction.CARD_CLICKED) }
        afterContentViewInflated()
    }

    protected open fun afterContentViewInflated() {}
    protected abstract fun onCreateContentView(inflater: LayoutInflater, rootView: CardView, context: Context, attributeSet: AttributeSet?): View
    protected abstract fun onCardChanged(card: Pattern?)

}