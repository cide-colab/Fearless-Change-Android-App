package de.thkoeln.fherborn.fearlesschange.v2.ui.customs.card

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.AdaptableView
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.SingleViewAdapter

class PatternCardFront @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr), AdaptableView<PatternCardFront> {

    private val delegation = PatternCardDelegation(this)
    var title by delegation.updateTitle(R.id.card_front_title)
    var summary by delegation.updateSummary(R.id.card_front_summary)
    var imageId by delegation.updateCardImage(R.id.card_front_image)
    var favorite by delegation.updateFavoriteFab(R.id.card_front_favorite_btn)
    var background by delegation.updateCardBackground(R.id.card_front_wrapper)
    var contentBackground by delegation.updateCardContentBackground(R.id.card_front_content)
    var onFavoriteClickedListener by delegation.updateClickListener(R.id.card_front_favorite_btn)
    var onCardClickedListener by delegation.updateClickListener(R.id.card_front_card)

    init {
        View.inflate(context, R.layout.pattern_card_front, this)
        context?.obtainStyledAttributes(attrs, R.styleable.PatternCardFront, defStyleAttr, 0)?.apply {
            try {
                favorite = getBoolean(R.styleable.PatternCardFront_PatternCardFavorite, delegation.favoriteDefault)
                title = getString(R.styleable.PatternCardFront_PatternCardTitle) ?: delegation.titleDefault
                summary = getString(R.styleable.PatternCardFront_PatternCardSummary) ?: delegation.summaryDefault
                imageId = getInt(R.styleable.PatternCardFront_PatternCardImage, delegation.imageDefault)
                background = getInt(R.styleable.PatternCardFront_PatternCardBackground, delegation.backgroundDefault)
                contentBackground = getInt(R.styleable.PatternCardFront_PatternCardContentBackground, delegation.contentBackgroundDefault)
            } finally {
                recycle()
            }
        }
    }

    override fun <T> setAdapter(adapter: SingleViewAdapter<T, PatternCardFront>) {
        adapter.registerView(this)
    }

}