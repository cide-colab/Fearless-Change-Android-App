package de.thkoeln.colab.fearlesschange.view.label

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import de.thkoeln.colab.fearlesschange.R
import kotlinx.android.synthetic.main.label.view.*

/**
 * TODO: document your custom view class.
 */
class Label : ConstraintLayout {

    var icon: Int? = null
        set(value) {
            field = value
            invalidateTextPaintAndMeasurements()
        }

    var text: String? = null
        set(value) {
            field = value
            invalidateTextPaintAndMeasurements()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        inflate(context, R.layout.label, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.Label, defStyle, 0)

        label_text.text = a.getString(R.styleable.Label_labelText)


//        gradientTopColor = a.getColor(R.styleable.Label_gradientTopColor, gradientTop)
//        gradientBottomColor = a.getColor(R.styleable.Label_gradientBottomColor,  gradientBottom)
//        contentColor = a.getColor(R.styleable.Label_contentColor,  gradientBottom)
//
        if (a.hasValue(R.styleable.Label_labelIcon)) {
            label_icon.setImageDrawable(a.getDrawable(R.styleable.Label_labelIcon))
        }
//
//        a.recycle()
//
//        // Set up a default TextPaint object
//        textPaint = TextPaint().apply {
//            flags = Paint.ANTI_ALIAS_FLAG
//            textAlign = Paint.Align.LEFT
//        }
//
//        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
//        with(textPaint) {
//            //textSize = exampleDimension
//            color = contentColor
//            textWidth = measureText(text)
//            textHeight = fontMetrics.bottom
//        }
    }

}
