package de.thkoeln.colab.fearlesschange.view.editor

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import com.google.gson.GsonBuilder
import de.thkoeln.colab.fearlesschange.core.NoArg
import java.io.Serializable


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
