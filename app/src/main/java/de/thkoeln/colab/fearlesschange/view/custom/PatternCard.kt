@file:Suppress("LeakingThis")

package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import kotlinx.android.synthetic.main.pattern_card.view.*

abstract class PatternCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    var pattern: Pattern? = null
        set(value) {
            field = value
            applyAttributes(pattern)
        }

    init {
        inflateParentView(context)
        inflateChildLayout(context, pattern_card_card_view)?.apply {
            isDuplicateParentStateEnabled = true
        }
    }

    protected open fun inflateParentView(context: Context) {
        inflate(context, R.layout.pattern_card, this)
    }

    protected open fun inflateChildLayout(context: Context, parent: ViewGroup): View? = null
    protected abstract fun applyAttributes(pattern: Pattern?)


}
