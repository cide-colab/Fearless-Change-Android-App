package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import android.content.Context
import android.util.AttributeSet
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import android.view.LayoutInflater
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R


/**
 * Created by florianherborn on 30.07.18.
 */
class CardViewBack : CardView {

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun init(context: Context, attrs: AttributeSet?) {
        super.init(context, attrs)
//        context.theme.obtainStyledAttributes(attrs, R.styleable.CardBackView, 0, 0).apply {
//            try {
//                card_title.text = getString(R.styleable.CardViewBack_cardTitle)
//                card_solution.text = getString(R.styleable.CardViewBack_cardProblem)
//                card_buts.text = getString(R.styleable.CardViewBack_cardSolution)
//            } finally {
//                recycle()
//            }
//        }
    }
    override fun onCreateContentView(inflater: LayoutInflater, rootView: CardView, context: Context, attributeSet: AttributeSet?): View
            = inflater.inflate(R.layout.card_back, rootView, false)

    override fun afterContentViewInflated() {
//        card_front_favorite_btn.setOnClickListener{
//            performAction( card, CardViewAction.FAVORITE_CLICKED)
//            onCardChanged(card)
//        }
    }

    override fun onCardChanged(card: Card?) {
//        card?.let {
//            card_front_favorite_btn.setImageResource(
//                    if(it.favorite) R.drawable.ic_favorite_full_white
//                    else R.drawable.ic_favorite_white
//            )
//            card_front_title.text = it.title
//            card_front_abstract.text = it.solution
//            card_solution.text = it.summary
//        }
    }

}