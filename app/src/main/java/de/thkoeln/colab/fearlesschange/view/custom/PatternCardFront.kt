package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.getDrawableId
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import kotlinx.android.synthetic.main.pattern_card_front.view.*

class PatternCardFront @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : PatternCard(context, attrs, defStyleAttr) {

    override fun inflateChildLayout(context: Context, parent: ViewGroup): View =
            inflate(context, R.layout.pattern_card_front, parent)


    override fun applyAttributes(pattern: Pattern?) {

        pattern_card_front_image
                .setImageResource(pattern?.let { context.getDrawableId(it.pictureName) }
                        ?: R.drawable.default_pattern_image)
        pattern_card_front_title.text = pattern?.title ?: ""
        pattern_card_front_summary.text = pattern?.summary ?: ""

        invalidate()
        requestLayout()
    }

}
