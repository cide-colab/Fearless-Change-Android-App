package de.thkoeln.fherborn.fearlesschange.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.util.AttributeSet
import de.thkoeln.fherborn.fearlesschange.db.Card
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.R
import kotlinx.android.synthetic.main.layout_card_large.view.*


/**
 * Created by florianherborn on 30.07.18.
 */
class CardViewLarge: ConstraintLayout {

    constructor(context: Context): super(context) { init(context, null) }
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) { init(context, attributeSet) }
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) { init(context, attributeSet) }

    var card: Card? = null

    private fun init(context: Context, attributeSet: AttributeSet?) {

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_card_large, this, true)

        card?.let {
            card_title.text = it.title
            card_problem.text = it.problem
        }


    }


}