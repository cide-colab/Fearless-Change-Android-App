package de.thkoeln.colab.fearlesschange.view.editor

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.util.AttributeSet
import android.widget.*
import com.google.gson.GsonBuilder
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.NoArg
import de.thkoeln.colab.fearlesschange.core.toPx
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


enum class EditorState {
    BOLD,
    ITALIC,
    UNDERLINE,
    CHECKBOX
}

typealias OnStateChangeListener = (states: List<EditorState>) -> Unit

class RichTextEditorToolbar : HorizontalScrollView, OnStateChangeListener {

    var editor: RichTextEditor? = null
        set(value) {
            field = value
            field?.onStateChangeListener = this
        }

    private lateinit var container: LinearLayout
    private val actions = mutableMapOf<EditorState, ImageButton>()

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
        container = LinearLayout(context)
        container.orientation = LinearLayout.HORIZONTAL
        addView(container)

        addAction(R.drawable.ic_format_bold_black_24dp, EditorState.BOLD)
        addAction(R.drawable.ic_format_italic_black_24dp, EditorState.ITALIC)
        addAction(R.drawable.ic_format_underlined_black_24dp, EditorState.UNDERLINE)
        addAction(android.R.drawable.checkbox_on_background, EditorState.CHECKBOX)
    }

    private fun addAction(icon: Int, state: EditorState) {
        val params = LayoutParams(24.toPx(), 24.toPx())
        params.leftMargin = 2.toPx()
        params.rightMargin = 2.toPx()

        val button = ImageButton(context)
        button.setImageResource(icon)
        button.layoutParams = params
        button.setBackgroundResource(android.R.color.transparent)
        button.setOnClickListener { editor?.setState(state) }
        actions[state] = button
        container.addView(button)
    }

    override fun invoke(states: List<EditorState>) {
        actions.filterKeys { it != EditorState.CHECKBOX }.forEach { (state, btn) ->
            if (states.contains(state)) {
                btn.setBackgroundColor(resources.getColor(R.color.secondaryDark))
                btn.setColorFilter(resources.getColor(R.color.secondaryText))
            } else {
                btn.setBackgroundColor(resources.getColor(android.R.color.transparent))
                btn.clearColorFilter()
            }
        }
    }
}

class RichTextEditor : EditText {

    private var initialized = false

    private lateinit var spanManager: SpanManager

    var onStateChangeListener: OnStateChangeListener = {}

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
        initialized = true
    }

    fun setState(state: EditorState) {
        val span: Span = when (state) {
            EditorState.BOLD -> BoldSpan()
            EditorState.ITALIC -> ItalicSpan()
            EditorState.UNDERLINE -> ULineSpan()
            EditorState.CHECKBOX -> CheckBoxSpan(context)
        }
        setSpan(span)
    }

    fun setBold() {
        setSpan(BoldSpan())
    }

    fun setItalic() {
        setSpan(ItalicSpan())
    }

    fun setUnderline() {
        setSpan(ULineSpan())
    }

    fun setCheckbox() {
        setSpan(CheckBoxSpan(context))
    }

    fun setSpan(span: Span) {
        spanManager.addState(span)
        updateState()
    }

    fun updateState() {
        val states = spanManager.currentState.mapNotNull {
            when (it) {
                is BoldSpan -> EditorState.BOLD
                is ItalicSpan -> EditorState.ITALIC
                is ULineSpan -> EditorState.UNDERLINE
                is CheckBoxSpan -> EditorState.CHECKBOX
                else -> null
            }
        }
        onStateChangeListener(states)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (!initialized) return
        spanManager.notifySelectionChanged()
        updateState()
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
