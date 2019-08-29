package de.thkoeln.colab.fearlesschange.view

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import com.google.gson.*
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.NoArg
import java.io.Serializable
import java.lang.reflect.Type
import kotlin.math.max
import kotlin.math.min


/**
 * TODO: document your custom view class.
 */

interface Clickable {
    fun onClick(view: TextView, spannable: Spannable, event: MotionEvent)
}

class CheckBoxSpan(context: Context, val state: Boolean = false) : ImageSpan(context, if (state) R.drawable.ic_check_box_black_24dp else R.drawable.ic_check_box_outline_blank_black_24dp), Clickable {

    override fun onClick(view: TextView, spannable: Spannable, event: MotionEvent) {
        Log.d("CLICK", "CLICK")
        val start = spannable.getSpanStart(this)
        val end = spannable.getSpanEnd(this)
        spannable.removeSpan(this)
        spannable.setSpan(CheckBoxSpan(view.context, !state), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

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
            val link = buffer.getSpans(off, off, Clickable::class.java)

            link.forEach { it.onClick(widget, buffer, event) }
        }
        return super.onTouchEvent(widget, buffer, event)
    }
}

//class ClickExtensionSpan(private val clickable: Clickable) : ClickableSpan() {
//    override fun onClick(v: View) {
//        clickable.onClick(v)
//    }
//}

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


    fun setBold() {
        updateSpan(StyleSpan(Typeface.BOLD))
    }

    fun setItalic() {
        updateSpan(CheckBoxSpan(context)) { true }
        updateSpan(StyleSpan(Typeface.ITALIC))
    }

    fun setUnderline() {
        updateSpan(UnderlineSpan())
    }

    fun updateSpan(span: StyleSpan) = updateSpan(span) { style == it.style }
    fun updateSpan(span: UnderlineSpan) = updateSpan(span) { true }
    fun updateSpan(span: ClickableSpan) = updateSpan(span) { true }

    private fun <T : Any> updateSpan(span: T, equalsSpan: T.(other: T) -> Boolean): T? {
        return when (selectionStart) {
            -1 -> {
                // TODO no focus?
                null
            }
            selectionEnd -> {
                // TODO nothing selected
                null
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
                    null
                } else {
                    setSpan(span, selectStart, selectEnd)
                    span
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
        movementMethod = AdvancedMovementMethod()
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

@NoArg
data class StyledText(val text: String, val spanInfos: List<SpanInfo>) : Serializable

@NoArg
interface SpanInfo : Serializable {
    val from: Int
    val to: Int
}

@NoArg
data class StyleSpanInfo(override val from: Int, override val to: Int, val formatCode: Int) : SpanInfo

@NoArg
data class UnderlineSpanInfo(override val from: Int, override val to: Int) : SpanInfo

@NoArg
data class CheckboxSpanInfo(override val from: Int, override val to: Int, val state: Boolean) : SpanInfo

interface RichTextViewCore {

    fun setHtml(value: String) {
        Log.d("HTML: ", value)

        val styledText = getGson().fromJson(value, StyledText::class.java)

        val spannable = SpannableString(styledText.text)

        styledText.spanInfos.forEach {
            fun setSpan(span: Any) = spannable.setSpan(span, it.from, it.to, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            when (it) {
                is StyleSpanInfo -> setSpan(StyleSpan(it.formatCode))
                is UnderlineSpanInfo -> setSpan(UnderlineSpan())
                is CheckboxSpanInfo -> setSpan(CheckBoxSpan(getContext(), it.state))
            }
        }

        setText(spannable)
    }

    fun getHtml(): String {

        val spannable = getEditableText()
        val infos = spannable.getSpans(0, spannable.length, Any::class.java).mapNotNull {
            val start = spannable.getSpanStart(it)
            val end = spannable.getSpanEnd(it)
            when (it) {
                is StyleSpan -> StyleSpanInfo(start, end, it.style)
                is UnderlineSpan -> UnderlineSpanInfo(start, end)
                is CheckBoxSpan -> CheckboxSpanInfo(start, end, it.state)
                else -> null
            }
        }

        Log.d("SPANS", infos.toString())

        return getGson().toJson(StyledText(spannable.toString(), infos))

    }

    private fun getGson() = GsonBuilder().registerTypeAdapter(SpanInfo::class.java, SpanInfoInterfaceAdapter()).create()
    fun setText(string: CharSequence)
    fun getEditableText(): Editable
    fun setSpan(span: Any, from: Int, to: Int) = getEditableText().setSpan(span, from, to, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    fun getContext(): Context
}

class SpanInfoInterfaceAdapter : JsonDeserializer<Any>, JsonSerializer<Any> {

    companion object {
        const val CLASSNAME = "CLASSNAME"
        const val DATA = "DATA"
    }

    @Throws(JsonParseException::class)
    override fun deserialize(jsonElement: JsonElement, type: Type,
                             jsonDeserializationContext: JsonDeserializationContext): Any {

        val jsonObject = jsonElement.asJsonObject
        val prim = jsonObject.get(CLASSNAME) as JsonPrimitive
        val className = prim.asString
        val objectClass = getObjectClass(className)
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), objectClass)
    }

    override fun serialize(jsonElement: Any, type: Type, jsonSerializationContext: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty(CLASSNAME, jsonElement.javaClass.name)
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement))
        return jsonObject
    }

    private fun getObjectClass(className: String): Class<*> {
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e.message)
        }

    }
}