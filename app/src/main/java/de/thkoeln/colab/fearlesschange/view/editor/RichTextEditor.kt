package de.thkoeln.colab.fearlesschange.view.editor

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import com.google.gson.GsonBuilder
import de.thkoeln.colab.fearlesschange.core.NoArg
import de.thkoeln.colab.fearlesschange.core.toPx
import java.io.Serializable
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.KClass


/**
 * TODO: onTextChange to save checkboxes
 * TODO: Note Style
 * TODO: Lines between notes
 * TODO: Split Toolbar
 * TODO: Bottombar is before toasts
 * TODO: dont show Bottom-Bar on Note-Detail etc.
 * TODO: move toolbar to keyboard
 * TODO: Labels
 */

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

private fun List<Any>?.containsType(obj: Any) = this != null && find { it::class == obj::class } != null

private data class SpanHolder(var start: Int, var end: Int, val span: Span)

private fun List<SpanHolder>.getSpans() = map { it.span }

private fun List<SpanHolder>.filterInRange(start: Int, end: Int) = filter {
    it.start in start..end
            || it.end in start..end
            || start in it.start..it.end
            || end in it.start..it.end
}


private fun <T : Span> List<SpanHolder>.filterType(spanClass: KClass<T>) = filter { it.span::class == spanClass }
private fun <T : Span> List<SpanHolder>.get(start: Int, end: Int, spanClass: KClass<T>) = filterType(spanClass).filterInRange(start, end)
private fun List<SpanHolder>.getLike(start: Int, end: Int, span: Span) = get(start, end, span::class)
private fun List<SpanHolder>.getLike(holder: SpanHolder) = getLike(holder.start, holder.end, holder.span)

class SpanManager private constructor(private val editor: EditText) : TextWatcher {

    private val selectionStart
        get() = min(editor.selectionStart, editor.selectionEnd)

    private val selectionEnd
        get() = max(editor.selectionStart, editor.selectionEnd)

    private val hasNoFocus
        get() = editor.selectionStart == -1

    private val hasNoSelection
        get() = editor.selectionStart == editor.selectionEnd

    private val text
        get() = editor.text

    private val spans = mutableListOf<SpanHolder>()
    private val clicked = mutableListOf<Span>()

    fun <T : Span> setSpan(span: T) {
        when {
            hasNoFocus -> { /* TODO no focus */
            }
            hasNoSelection && span is InlineSpan -> addInlineSpan(span)
            hasNoSelection && span is InPlaceSpan -> addInPlaceSpan(selectionStart, span)
            span is InlineSpan -> setSpan(selectionStart, selectionEnd, span)
            span is InPlaceSpan -> setSpan(selectionStart, selectionEnd, span) // Todo delete text before set span
        }
    }

    private fun addInlineSpan(span: InlineSpan) {
        if (!clicked.containsType(spans)) {
            clicked.add(span)
        } else {
            clicked.remove(span)
        }
    }

    private fun addInPlaceSpan(index: Int, span: InPlaceSpan) {
        editor.text = text.insert(index, PLACEHOLDER)
        setSpan(index, index + PLACEHOLDER.length, span)
        editor.setSelection(index + PLACEHOLDER.length)
    }

    private fun setSpan(start: Int, end: Int, span: Span) {
        val holder = SpanHolder(start, end, span)
        val existing = spans.getLike(holder)
        if (existing.isEmpty()) attachSpan(holder)
        else updateSpan(existing, holder)
    }

    private fun updateSpan(existing: List<SpanHolder>, newSpan: SpanHolder) {
        val first = existing.minBy { it.start } ?: newSpan
        val last = existing.maxBy { it.end } ?: newSpan


        if (first.start == newSpan.end) {
            first.start = newSpan.start
            updateText()
            return
        }

        if (last.end == newSpan.start) {
            last.end = newSpan.end
            updateText()
            return
        }

        removeSpans(existing)


        if (first.end == newSpan.start && last.start == newSpan.end && last.span != first.span) {
            attachSpan(newSpan.copy(start = first.start, end = last.end, span = newSpan.span.copy()))
            updateText()
            return
        }

        if (first.start < newSpan.start) {
            attachSpan(newSpan.copy(start = first.start, end = newSpan.start, span = newSpan.span.copy()))
        }

        if (last.end > newSpan.end) {
            attachSpan(newSpan.copy(start = newSpan.end, end = last.end, span = newSpan.span.copy()))
        }
    }


    private fun attachSpan(newSpan: SpanHolder) {
        spans.add(newSpan)
        updateText()
    }

    private fun removeSpans(spans: List<SpanHolder>) {
        this.spans.removeAll(spans)
        updateText()
    }

//    private fun addSpans(spans: List<SpanHolder>) {
//        spans.forEach { text.setSpan(it.span, it.start, it.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
//    }

    override fun afterTextChanged(spannable: Editable) {
//        addSpans(spans.allSpans)
    }

    override fun beforeTextChanged(t: CharSequence?, changeStart: Int, before: Int, count: Int) {
        //removeSpans(spans.allSpans)
    }

    override fun onTextChanged(t: CharSequence?, start: Int, before: Int, count: Int) {
        val added = addedCharacters(count, before)
        val changeStart = start + before
        val changeEnd = changeStart + added
        updateExisting(changeStart, added)
        addNewStyles(changeStart, changeEnd)
    }

    private fun addNewStyles(changeStart: Int, changeEnd: Int) {
        clicked.forEach { setSpan(changeStart, changeEnd, it) }
        clicked.clear()
    }

    private fun updateExisting(start: Int, added: Int) {
        // start after -> start & end + added
        // end after -> end + added
        val toRemove = spans.mapNotNull {


            if (it.span is InPlaceSpan) {
                if (it.start >= start) {
                    it.start += added
                    it.end += added
                }
                if (added < 0 && start + added < it.end && start >= it.end) {
                    return@mapNotNull it
                }
                return@mapNotNull null
            }

            if (it.start >= start) {
                it.start += added
            }

            if (it.end >= start) {
                it.end += added
            }

            if (it.end <= it.start) {
                return@mapNotNull it
            }
            null
        }
        spans.removeAll(toRemove)

        updateText()
    }

    private fun updateText() {
        val invalidSpans = spans.filter { it.start >= text.length }
        spans.removeAll(invalidSpans)
        text.getSpans(0, text.length, Span::class.java)
                .filter { tSpan -> spans.find { it.span == tSpan } == null }
                .forEach { text.removeSpan(it) }
        spans.forEach { text.setSpan(it.span, it.start, it.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
    }

    private fun addedCharacters(count: Int, before: Int) = count - before


    companion object {
        private const val PLACEHOLDER = "\u2610"
        fun forEditor(editor: EditText) = SpanManager(editor).also {
            editor.addTextChangedListener(it)
        }
    }

}

class RichTextEditor : EditText {

    private lateinit var spanManager: SpanManager

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
        spanManager = SpanManager.forEditor(this)
    }

    fun setBold() {
        spanManager.setSpan(BoldSpan())
    }

    fun setItalic() {
        spanManager.setSpan(ItalicSpan())
    }

    fun setUnderline() {
        spanManager.setSpan(ULineSpan())
    }

    fun setCheckbox() {
        spanManager.setSpan(CheckBoxSpan(context))
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


class RichTextView : TextView {

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
data class ItalicSpanInfo(override val from: Int, override val to: Int) : SpanInfo

@NoArg
data class BoldSpanInfo(override val from: Int, override val to: Int) : SpanInfo

@NoArg
data class ULineSpanInfo(override val from: Int, override val to: Int) : SpanInfo

@NoArg
data class CheckboxSpanInfo(override val from: Int, override val to: Int, val state: Boolean) : SpanInfo

private fun getSpanGson() = GsonBuilder().registerTypeAdapter(SpanInfo::class.java, SpanInfoInterfaceAdapter()).create()
//private fun Spannable.setSpan(span: Any, from: Int, to: Int) = setSpan(span, from, to, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
private fun TextView.setSpan(span: Any, from: Int, to: Int) = editableText.setSpan(span, from, to, if (span is WritableSpan) Spannable.SPAN_EXCLUSIVE_EXCLUSIVE else Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


fun TextView.setJson(value: String) {

    val styledText = getSpanGson().fromJson(value, StyledText::class.java)
    val spannable = SpannableString(styledText.text)

    styledText.spanInfos.forEach {
        fun setSpan(span: Any) = spannable.setSpan(span, it.from, it.to, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        when (it) {
            is ItalicSpanInfo -> setSpan(ItalicSpan())
            is BoldSpanInfo -> setSpan(BoldSpan())
            is ULineSpanInfo -> setSpan(ULineSpan())
            is CheckboxSpanInfo -> setSpan(CheckBoxSpan(context, it.state))
        }
    }

    text = spannable
}

fun TextView.getJson(): String {

    val spannable = editableText
    val infos = spannable.getSpans(0, spannable.length, Any::class.java).mapNotNull {
        val start = spannable.getSpanStart(it)
        val end = spannable.getSpanEnd(it)
        when (it) {
            is BoldSpan -> BoldSpanInfo(start, end)
            is ItalicSpan -> ItalicSpanInfo(start, end)
            is ULineSpan -> ULineSpanInfo(start, end)
            is CheckBoxSpan -> CheckboxSpanInfo(start, end, it.state)
            else -> null
        }
    }
    return getSpanGson().toJson(StyledText(spannable.toString(), infos))

}
