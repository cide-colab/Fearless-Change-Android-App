package de.thkoeln.fherborn.fearlesschange.v2.ui.customs.card

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.AdaptableView
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.SingleViewAdapter

class PatternCardBack @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr), AdaptableView<PatternCardBack> {

    private val delegation = PatternCardDelegation(this)
    var title by delegation.updateTitle(R.id.card_back_title)
    var problem by delegation.updateProblem(R.id.card_back_problem)
    var solution by delegation.updateSolution(R.id.card_back_solution)
    var favorite by delegation.updateFavoriteFab(R.id.card_back_favorite_btn)
    var background by delegation.updateCardBackground(R.id.card_back_wrapper)
    var contentBackground by delegation.updateCardContentBackground(R.id.card_back_content)
    var onFavoriteClickedListener by delegation.updateClickListener(R.id.card_back_favorite_btn)
    var onCardClickedListener by delegation.updateClickListener(R.id.card_back_card)

    init {
        View.inflate(context, R.layout.card_back, this)
        context?.obtainStyledAttributes(attrs, R.styleable.PatternCardBack, defStyleAttr, 0)?.apply {
            try {
                favorite = getBoolean(R.styleable.PatternCardBack_PatternCardFavorite, delegation.favoriteDefault)
                title = getString(R.styleable.PatternCardBack_PatternCardTitle) ?: delegation.titleDefault
                problem = getString(R.styleable.PatternCardBack_PatternCardProblem) ?: delegation.problemDefault
                solution = getString(R.styleable.PatternCardBack_PatternCardSolution) ?: delegation.solutionDefault
                background = getInt(R.styleable.PatternCardBack_PatternCardBackground, delegation.backgroundDefault)
                contentBackground = getInt(R.styleable.PatternCardBack_PatternCardContentBackground, delegation.contentBackgroundDefault)
            } finally {
                recycle()
            }
        }
    }

    override fun <T> setAdapter(adapter: SingleViewAdapter<T, PatternCardBack>) {
        adapter.registerView(this)
    }

}