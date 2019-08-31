package de.thkoeln.colab.fearlesschange.view.editor

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView

class AdvancedMovementMethod : LinkMovementMethod() {
    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            var x = event.x
            var y = event.y

            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop

            x += widget.scrollX
            y += widget.scrollY

            val layout = widget.layout
            val line = layout.getLineForVertical(y.toInt())
            val off = layout.getOffsetForHorizontal(line, x)
            val link = buffer.getSpans(off, off, ClickableSpan::class.java)

            link.forEach { it.onClick(widget, buffer, event) }
        }
        return super.onTouchEvent(widget, buffer, event)
    }
}