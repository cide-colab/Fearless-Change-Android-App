package de.thkoeln.colab.fearlesschange.view.editor

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.MotionEvent
import android.widget.TextView
import de.thkoeln.colab.fearlesschange.core.toPx


interface Span {
    fun copy(): Span
}

interface InlineSpan : Span
interface InPlaceSpan : Span

interface WritableSpan

interface ClickableSpan {
    fun onClick(view: TextView, spannable: Spannable, event: MotionEvent)
}

class CheckBoxSpan(private val context: Context, val state: Boolean = false) :
        ImageSpan(context, if (state) android.R.drawable.checkbox_on_background else android.R.drawable.checkbox_off_background), ClickableSpan, InPlaceSpan {

    override fun copy(): CheckBoxSpan = CheckBoxSpan(context, state)

    override fun onClick(view: TextView, spannable: Spannable, event: MotionEvent) {
        val start = spannable.getSpanStart(this)
        val end = spannable.getSpanEnd(this)
        spannable.removeSpan(this)
        spannable.setSpan(CheckBoxSpan(view.context, !state), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    override fun getDrawable(): Drawable {
        return super.getDrawable().apply { setBounds(0, 0, 16.toPx(), 16.toPx()) }
    }
}

class BoldSpan : StyleSpan(Typeface.BOLD), WritableSpan, InlineSpan {
    override fun copy(): BoldSpan = BoldSpan()
}

class ItalicSpan : StyleSpan(Typeface.ITALIC), WritableSpan, InlineSpan {
    override fun copy(): ItalicSpan = ItalicSpan()
}

class ULineSpan : UnderlineSpan(), WritableSpan, InlineSpan {
    override fun copy(): ULineSpan = ULineSpan()
}