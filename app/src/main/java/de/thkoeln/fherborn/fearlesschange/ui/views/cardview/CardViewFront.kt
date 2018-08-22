package de.thkoeln.fherborn.fearlesschange.ui.views.cardview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.R


/**
 * Created by florianherborn on 30.07.18.
 */
class CardViewFront: ConstraintLayout {

    constructor(context: Context): super(context) { init(context, null) }
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) { init(context, attributeSet) }
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) { init(context, attributeSet) }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.card_front, this, true)
//
//        context.theme.obtainStyledAttributes(attrs, R.styleable.CardViewFront, 0, 0).apply {
//            try {
//                card_front_title.text = getString(R.styleable.CardViewFront_cardTitle)
//                card_front_abstract.text = getString(R.styleable.CardViewFront_cardSummary)
//                val isFavorite = getBoolean(R.styleable.CardViewFront_isFavorite, false)
//                card_front_favorite_btn.setImageResource(
//                        if(isFavorite) R.drawable.ic_favorite_full_white
//                        else R.drawable.ic_favorite_white
//                )
//                GlideApp.with(context)
//                        .load(getDrawable(R.styleable.CardViewFront_cardImage))
//                        .fitCenter()
//                        .into(card_front_image)
//            } finally {
//                recycle()
//            }
//        }
    }

    fun setOnFavoriteClickedListener(listener: OnClickListener) {
//        card_front_favorite_btn.setOnClickListener(listener)
    }
}