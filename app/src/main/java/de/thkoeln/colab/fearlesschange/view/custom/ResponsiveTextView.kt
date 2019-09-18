package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.toPx
import kotlin.math.max


class ResponsiveTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    var charactersPerRow = 40
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    init {
        ellipsize = TextUtils.TruncateAt.END
        val a = context.obtainStyledAttributes(attrs, R.styleable.ResponsiveTextView, defStyleAttr, 0)
        charactersPerRow = a.getInt(R.styleable.ResponsiveTextView_charactersPerRow, charactersPerRow)
        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w != oldw) {
            refitMeasurements(w, h)
        }
    }

    private fun refitMeasurements(textWidth: Int, h: Int? = null) {
        if (textWidth <= 0) return
        val hPadding = ((textWidth.toFloat() / 50.toPx()) * 2.toPx()).toInt()
        setPadding(hPadding, paddingTop, hPadding, paddingBottom)
        val targetWidth = textWidth - 2 * hPadding

        val characterWidth = (targetWidth / charactersPerRow)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, characterWidth.toFloat())

        if (layoutParams.height != WRAP_CONTENT && h != null) {
            val height = (h.toFloat() - paddingTop - paddingBottom)
            maxLines = max((height / lineHeight).toInt(), 1)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val height = measuredHeight
        refitMeasurements(parentWidth)
        this.setMeasuredDimension(parentWidth, height)
    }

}
