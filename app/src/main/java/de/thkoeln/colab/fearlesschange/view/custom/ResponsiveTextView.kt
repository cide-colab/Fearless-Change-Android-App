package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.view.setPadding
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

        val padding = max((w / 50.toPx()), 1) * 2.toPx()

        val width = (w.toFloat() - 2 * padding)
        val characterWidth = width / charactersPerRow

        setPadding(padding)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, characterWidth)

        if (layoutParams.height != WRAP_CONTENT) {
            val height = (h.toFloat() - 2 * padding)
            maxLines = max((height / lineHeight).toInt(), 1)
        }
    }

}
