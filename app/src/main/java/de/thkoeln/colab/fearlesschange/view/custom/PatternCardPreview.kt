package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import kotlinx.android.synthetic.main.pattern_card_preview.view.*

class PatternCardPreview @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    var patternPreviewData: PatternPreviewData? = null
        set(value) {
            field = value
            applyAttributes(patternPreviewData)
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

    private fun applyAttributes(patternPreviewData: PatternPreviewData?) {
        pattern_card_preview_pattern_card.pattern = patternPreviewData?.pattern
        val noteCount = patternPreviewData?.noteCount
        if (noteCount != null && noteCount != 0) {
            pattern_card_preview_note_count.text = "$noteCount"
            pattern_card_preview_note_count.visibility = VISIBLE
            pattern_card_preview_notes_icon.visibility = VISIBLE
        } else {
            pattern_card_preview_note_count.text = "0"
            pattern_card_preview_note_count.visibility = GONE
            pattern_card_preview_notes_icon.visibility = GONE
        }
        pattern_card_preview_fav_icon.visibility = if (patternPreviewData?.pattern?.favorite == true) VISIBLE else GONE
        invalidate()
        requestLayout()
    }

    private fun prepareView() {
        isClickable = true
        isFocusable = true
    }

}
