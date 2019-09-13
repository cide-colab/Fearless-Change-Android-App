package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInfo
import kotlinx.android.synthetic.main.pattern_card_preview.view.*

class PatternCardPreview @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    var patternInfo: PatternInfo? = null
        set(value) {
            field = value
            applyAttributes(patternInfo)
        }

    init {
        inflate(context, R.layout.pattern_card_preview, this)
        applyAttributes(context, attrs, defStyleAttr)
        prepareView()
    }

    private fun applyAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.PatternCardFront, defStyleAttr, 0)
//                color = a.getColor(R.styleable.ColorChip_color, color)
//        a.recycle()
    }

    private fun applyAttributes(patternInfo: PatternInfo?) {
        pattern_card_preview_pattern_card.pattern = patternInfo?.pattern

        invalidate()
        requestLayout()
    }

    private fun prepareView() {
        isClickable = true
        isFocusable = true
    }

}
