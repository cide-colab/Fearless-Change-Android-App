package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import kotlinx.android.synthetic.main.pattern_card_back.view.*

class PatternCardBack @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : PatternCard(context, attrs, defStyleAttr) {

    override fun inflateChildLayout(context: Context, parent: ViewGroup): View =
            inflate(context, R.layout.pattern_card_back, parent)


    override fun applyAttributes(pattern: Pattern?) {

        pattern_card_back_problem.text = pattern?.problem ?: ""
        pattern_card_back_solution.text = pattern?.solution ?: ""

        invalidate()
        requestLayout()
    }
}
