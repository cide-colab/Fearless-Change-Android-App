package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import kotlinx.android.synthetic.main.pattern_card_back.view.*

class PatternCardBack @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    var pattern: Pattern? = null
        set(value) {
            field = value
            applyAttributes(pattern)
        }

    init {
        inflate(context, R.layout.pattern_card_back, this)

    }

    private fun applyAttributes(pattern: Pattern?) {

        pattern_card_back_problem.text = pattern?.problem ?: ""
        pattern_card_back_solution.text = pattern?.solution ?: ""

        invalidate()
        requestLayout()
    }
}
