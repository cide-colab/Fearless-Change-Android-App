package de.thkoeln.colab.fearlesschange.view

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import com.google.gson.*
import de.thkoeln.colab.fearlesschange.core.NoArg
import java.io.Serializable
import java.lang.reflect.Type
import kotlin.math.max
import kotlin.math.min


/**
 * TODO: onTextChange to save checkboxes
 * TODO: Icon size
 * TODO: Box alignment
 * TODO: Note Style
 * TODO: Lines between notes
 * TODO: Split Toolbar
 * TODO: Bottombar is before toasts
 * TODO: dont show Bottom-Bar on Note-Detail etc.
 * TODO: move toolbar to keyboard
 * TODO: Labels
 * TODO: FIx
 */


interface Span

interface WritableSpan

interface ClickableSpan {
    fun onClick(view: TextView, spannable: Spannable, event: MotionEvent)
}

class CheckBoxSpan(context: Context, val state: Boolean = false) : ImageSpan(context, if (state) android.R.drawable.checkbox_on_background else android.R.drawable.checkbox_off_background), ClickableSpan, Span {

    override fun onClick(view: TextView, spannable: Spannable, event: MotionEvent) {
        val start = spannable.getSpanStart(this)
        val end = spannable.getSpanEnd(this)
        spannable.removeSpan(this)
        spannable.setSpan(CheckBoxSpan(view.context, !state), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
// TODO smaller icon
//    override fun getDrawable(): Drawable {
//        return super.getDrawable().apply { setBounds(0, 0, ) }
//    }
}

class BoldSpan : StyleSpan(Typeface.BOLD), WritableSpan, Span
class ItalicSpan : StyleSpan(Typeface.ITALIC), WritableSpan, Span
class ULineSpan : UnderlineSpan(), WritableSpan, Span

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

class RichTextEditor : EditText, RichTextViewCore {

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
    }


    fun setBold() {
        updateSpan(BoldSpan())
    }

    fun setItalic() {
        updateSpan(ItalicSpan())
    }

    fun setUnderline() {
        updateSpan(ULineSpan())
    }

    fun setCheckbox() {
        updateSpan(CheckBoxSpan(context))
    }

    private val clickedSpans: MutableList<WritableSpan>? = mutableListOf()

    private fun List<Any>?.containsType(obj: Any) = this != null && find { it::class == obj::class } != null

    override fun onTextChanged(t: CharSequence?, changeStart: Int, before: Int, count: Int) {
        super.onTextChanged(t, changeStart, before, count)
        if (count > before) {
            val tStart = changeStart + before
            val tEnd = changeStart + count
            val lastSpans = if (changeStart > 0) text.getSpans(tStart - 1, tStart, WritableSpan::class.java).toList() else listOf()

            val allSpans = lastSpans + (clickedSpans
                    ?: mutableListOf()).filter { !lastSpans.containsType(it) }

            val spansToAdd = allSpans.filter {
                when {
                    lastSpans.containsType(it) && clickedSpans.containsType(it) -> false
                    lastSpans.containsType(it) && !clickedSpans.containsType(it) -> true
                    else -> true
                }
            }

            // last ja %% clicked ja -> löschen TODO Split and use new style
            // last ja && clicked nein -> behalten
            // last nein && clicked ja -> hinzufügen
            // last nein && clicked nein -> nix


//            val potentiallyAdd = lastSpans + (newSpans ?: mutableListOf()).filter { new -> lastSpans.none { it::class == new::class } }
//            val finallyAdd = potentiallyAdd.filter { new -> removeSpans?.none { it::class == new::class }?:true }
            //TODO filter ob span genommen werden soll
            spansToAdd.forEach {
                val lastStart = text.getSpanStart(it)
                val lastEnd = text.getSpanEnd(it)
                val start = if (lastStart == -1) tStart else lastStart
                val end = if (lastEnd == -1) tEnd else max(tEnd, lastEnd)
                setSpan(it, start, end)
            }
            clickedSpans?.clear()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Span> setCurrentSpan(span: T) {
        if (span !is WritableSpan) return
        if (clickedSpans?.find { it::class == span::class } != null) {
            clickedSpans.remove(span)
        } else {
            clickedSpans?.add(span)
        }

//        newSpans?.find { span::class == it::class }
//                ?.let {
//                    newSpans.remove(it)
//                    removeSpans?.add(it)
//                }
//                ?: let {
//                    newSpans?.add(span)
//                    removeSpans?.remove(span)
//                }
    }


    private fun <T : Span> updateSpan(span: T) {
        when (selectionStart) {
            -1 -> {
                // TODO no focus?
            }
            selectionEnd -> when (span) {
                is CheckBoxSpan -> setSpan(selectionStart, span)
                else -> setCurrentSpan(span)

            }
            else -> {
                val selectStart = min(selectionStart, selectionEnd)
                val selectEnd = max(selectionStart, selectionEnd)

                val spans = text.getSpans(selectStart, selectEnd, span::class.java)
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


    private fun <T : Any> setSpan(index: Int, span: T) {
        val placeholder = "[]"
        text = text.insert(index, placeholder)
        setSpan(span, index, index + placeholder.length)
        setSelection(index + placeholder.length)
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