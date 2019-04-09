package de.thkoeln.colab.fearlesschange.ui.customs.card

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.helper.animation.FlipAnimationManager
import de.thkoeln.colab.fearlesschange.ui.adapter.SingleViewAdapter
import kotlinx.android.synthetic.main.pattern_card_flippable.view.*

/**
 * Created by florianherborn on 23.08.18.
 */
class FlippablePatternCard @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    private val flipAnimationHelper: FlipAnimationManager

    init {
        View.inflate(context, R.layout.pattern_card_flippable, this)

        flipAnimationHelper = FlipAnimationManager(card_front, card_back)

        card_front.onCardClickedListener = { flipAnimationHelper.flipToBack() }
        card_back.onCardClickedListener = { flipAnimationHelper.flipToFront() }
    }

    fun flip(animated: Boolean = false) = flipAnimationHelper.flip(animated)
    fun flipToFront(animated: Boolean = false) = flipAnimationHelper.flipToFront(animated)
    fun flipToBack(animated: Boolean = false) = flipAnimationHelper.flipToBack(animated)

    fun <T> setCardFrontAdapter(adapter: SingleViewAdapter<T, PatternCardFront>) {
        card_front.setAdapter(adapter)
    }

    fun <T> setCardBackAdapter(adapter: SingleViewAdapter<T, PatternCardBack>) {
        card_back.setAdapter(adapter)
    }
}