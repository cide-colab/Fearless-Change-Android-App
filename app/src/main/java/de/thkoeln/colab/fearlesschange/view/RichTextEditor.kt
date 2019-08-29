package de.thkoeln.colab.fearlesschange.view

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import org.xml.sax.XMLReader
import java.io.Serializable
import kotlin.math.max
import kotlin.math.min

/**
 * TODO: document your custom view class.
 */

class TestSpan : ClickableSpan() {

    var test = "Test"

    override fun onClick(v: View) {
        Log.d("Custom Span", "Clicked ${v::class.simpleName}")
        when (v) {
            is TextView -> Log.d("Custom Span", v.text.substring(v.selectionStart, v.selectionEnd))
            is EditText -> Log.d("Custom Span", v.text.substring(v.selectionStart, v.selectionEnd))
        }

    }
}


class CustomTagHandler : Html.TagHandler {
    override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader) {
        Log.d(CustomTagHandler::class.java.simpleName, "Opending: $opening, tag: $tag")
    }
}

class RichTextEditor : EditText, RichTextViewCore {

//    private var _exampleString: String? = null // TODO: use a default from R.string...
//    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
//    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...
//
//    private var textPaint: TextPaint? = null
//    private var textWidth: Float = 0f
//    private var textHeight: Float = 0f
//
//    /**
//     * The text to draw
//     */
//    var exampleString: String?
//        get() = _exampleString
//        set(value) {
//            _exampleString = value
//            invalidateTextPaintAndMeasurements()
//        }
//
//    /**
//     * The font color
//     */
//    var exampleColor: Int
//        get() = _exampleColor
//        set(value) {
//            _exampleColor = value
//            invalidateTextPaintAndMeasurements()
//        }
//
//    /**
//     * In the example view, this dimension is the font size.
//     */
//    var exampleDimension: Float
//        get() = _exampleDimension
//        set(value) {
//            _exampleDimension = value
//            invalidateTextPaintAndMeasurements()
//        }
//
//    /**
//     * In the example view, this drawable is drawn above the text.
//     */
//    var exampleDrawable: Drawable? = null

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
        // Load attributes
//        val a = context.obtainStyledAttributes(
//                attrs, R.styleable.RichTextEditor, defStyle, 0)
//
//        _exampleString = a.getString(
//                R.styleable.RichTextEditor_exampleString)
//        _exampleColor = a.getColor(
//                R.styleable.RichTextEditor_exampleColor,
//                exampleColor)
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        _exampleDimension = a.getDimension(
//                R.styleable.RichTextEditor_exampleDimension,
//                exampleDimension)
//
//        if (a.hasValue(R.styleable.RichTextEditor_exampleDrawable)) {
//            exampleDrawable = a.getDrawable(
//                    R.styleable.RichTextEditor_exampleDrawable)
//            exampleDrawable?.callback = this
//        }
//
//        a.recycle()
//
//        // Set up a default TextPaint object
//        textPaint = TextPaint().apply {
//            flags = Paint.ANTI_ALIAS_FLAG
//            textAlign = Paint.Align.LEFT
//        }

        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements()
    }

    private fun setSpan(span: Any, from: Int, to: Int) = text.setSpan(span, from, to, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    fun setBold() {
        updateSpan(StyleSpan(Typeface.BOLD))
    }

    fun setItalic() {
        updateSpan(TestSpan())
        updateSpan(StyleSpan(Typeface.ITALIC))
    }

    fun setUnderline() {
        updateSpan(UnderlineSpan())
    }

    fun updateSpan(span: StyleSpan) {
        updateSpan(span) { style == it.style }
    }

    fun updateSpan(span: UnderlineSpan) {
        updateSpan(span) { true }
    }

    fun updateSpan(span: ClickableSpan) {
        updateSpan(span) { true }
    }

    private fun <T : Any> updateSpan(span: T, equalsSpan: T.(other: T) -> Boolean) {
        when (selectionStart) {
            -1 -> {
                // TODO no focus?
            }
            selectionEnd -> {
                // TODO nothing selected
            }
            else -> {
                val selectStart = min(selectionStart, selectionEnd)
                val selectEnd = max(selectionStart, selectionEnd)

                val spans = text.getSpans(selectStart, selectEnd, span::class.java).filter { it.equalsSpan(span) }
                if (spans.isNotEmpty()) {
                    val firstStart = spans.map { text.getSpanStart(it) }.min() ?: selectStart
                    val lastEnd = spans.map { text.getSpanEnd(it) }.max() ?: selectEnd

                    // TODO FIX
                    // If               ABCDEFGHIJKLMNOPQURSTUVWXYZ
                    // Old Spans          |---------||-------|
                    // Change span             |--------|
                    // First span will be resetted...

                    spans.forEach { text.removeSpan(it) }

                    if (firstStart < selectStart) {
                        setSpan(span, firstStart, selectStart)
                    }

                    if (lastEnd > selectEnd) {
                        setSpan(span, selectEnd, lastEnd)
                    }
                } else {
                    setSpan(span, selectStart, selectEnd)
                }
            }
        }
    }


//    private fun invalidateTextPaintAndMeasurements() {
//        textPaint?.let {
//            it.textSize = exampleDimension
//            it.color = exampleColor
//            textWidth = it.measureText(exampleString)
//            textHeight = it.fontMetrics.bottom
//        }
//    }
}


class RichTextView : TextView, RichTextViewCore {
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
        isClickable = true
        movementMethod = LinkMovementMethod.getInstance()
    }


//    private fun invalidateTextPaintAndMeasurements() {
//        textPaint?.let {
//            it.textSize = exampleDimension
//            it.color = exampleColor
//            textWidth = it.measureText(exampleString)
//            textHeight = it.fontMetrics.bottom
//        }
//    }
}

sealed class SpanInfo : Serializable {
    abstract val from: Int
    abstract val to: Int
}

data class StyleSpanInfo(override val from: Int, override val to: Int, val formatCode: Int) : SpanInfo()
data class UnderlineSpanInfo(override val from: Int, override val to: Int) : SpanInfo()

interface RichTextViewCore {

    fun setHtml(value: String) {
        Log.d("HTML: ", value)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setText(Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY, null, CustomTagHandler()))
        } else {
            setText(Html.fromHtml(value))
        }

    }

    fun getHtml(): String {

        val spannable = getEditableText()
        val infos = spannable.getSpans(0, spannable.length, Any::class.java).mapNotNull {
            val start = spannable.getSpanStart(it)
            val end = spannable.getSpanEnd(it)
            when (it) {
                is StyleSpan -> StyleSpanInfo(start, end, it.style)
                is UnderlineSpan -> UnderlineSpanInfo(start, end)
                else -> null
            }
        }

        Log.d("SPANS", infos.toString())

        spannable.getSpans(0, spannable.length, TestSpan::class.java).forEach {
            val start = spannable.getSpanStart(it)
            val end = spannable.getSpanEnd(it)
            val text = spannable.subSequence(start, end)
            spannable.removeSpan(it)
            spannable.replace(start, end, "<test>$text</test>")
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.toHtml(spannable, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.toHtml(spannable)
        }
    }

    fun setText(string: CharSequence)
    fun getEditableText(): Editable
}