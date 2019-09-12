package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import de.thkoeln.colab.fearlesschange.core.toPx
import kotlin.math.max


class ResponsiveTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val hPadding = max((w / 50), 1) * 1.toPx()
        val vPadding = max((h / 50), 1) * 1.toPx()
        setPadding(hPadding, vPadding, hPadding, vPadding)

        maxLines = max((h - 2 * vPadding) / lineHeight, 1)
    }

}
