package de.thkoeln.fherborn.fearlesschange.v2.carddetail.custom

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideApp
import kotlinx.android.synthetic.main.card_front_view.view.*

/**
 * Created by florianherborn on 30.07.18.
 */
class CardFrontView : ConstraintLayout {

    constructor(context: Context): super(context) { init(context, null) }
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) { init(context, attributeSet) }
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) { init(context, attributeSet) }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.card_front_view, this, true)

        context.theme.obtainStyledAttributes(attrs, R.styleable.CardViewFront, 0, 0).apply {
            try {
                card_title.text = getString(R.styleable.CardFrontView_title)
                card_problem.text = getString(R.styleable.CardFrontView_summary)


                card_fav_btn.setImageResource(
                        when {
                            getBoolean(R.styleable.CardFrontView_favorite, false) -> R.drawable.ic_favorite_full_white
                            else -> R.drawable.ic_favorite_white
                        }
                )

                GlideApp.with(context)
                        .load(getDrawable(R.styleable.CardFrontView_image))
                        .fitCenter()
                        .into(card_image)
            } finally {
                recycle()
            }
        }
    }

    fun setOnFavoriteClickedListener(listener: OnClickListener) {
        card_fav_btn.setOnClickListener(listener)
    }
}