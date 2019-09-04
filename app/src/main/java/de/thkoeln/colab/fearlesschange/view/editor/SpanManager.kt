package de.thkoeln.colab.fearlesschange.view.editor

import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.KClass

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

//    private val spans = mutableListOf<SpanHolder>()
//    private val clicked = mutableListOf<Span>()

    var currentState = listOf<Span>()
        private set

    private val currentSpans = mutableListOf<Triple<Int, Int, Span>>()

//    fun <T : Span> setSpan(span: T) {
    //TODO check if needed
//        when {
//            hasNoFocus -> { /* no focus */
//            }
//            hasNoSelection && span is InlineSpan -> addInlineSpan(span)
//            hasNoSelection && span is InPlaceSpan -> addInPlaceSpan(selectionStart, span)
//            span is InlineSpan -> setSpan(selectionStart, selectionEnd, span)
//            span is InPlaceSpan -> setSpan(selectionStart, selectionEnd, span) // Todo delete text before set span
//        }
//    }
//
//    private fun addInlineSpan(span: InlineSpan) {
    //TODO check if needed
//        if (!clicked.containsType(span)) {
//            clicked.add(span)
//        } else {
//            clicked.remove(span)
//        }
//    }
//
//    private fun addInPlaceSpan(index: Int, span: InPlaceSpan) {
    //TODO add with placeholder
//        editor.text = text.insert(index, PLACEHOLDER)
//        setSpan(index, index + PLACEHOLDER.length, span)
//        editor.setSelection(index + PLACEHOLDER.length)
//    }

    fun notifySelectionChanged(keepState: Boolean = false) {
        val selectionSpans = getSelectionSpans().map { it.third }
        currentState = if (keepState) selectionSpans + currentState.filter { selectionSpans.containsType(it) } else selectionSpans
    }

    private fun getSelectionSpans(): List<Triple<Int, Int, Span>> {
        return getSpans(selectionStart, selectionEnd)
    }

//    private fun setSpan(start: Int, end: Int, span: Span) {
//        val holder = SpanHolder(start, end, span)
//        val existing = spans.getLike(holder)
//        if (existing.isEmpty()) attachSpan(holder)
//        else updateSpan(existing, holder)
//    }
//
//    private fun updateSpan(existing: List<SpanHolder>, newSpan: SpanHolder) {
//
//        val first = existing.minBy { it.start } ?: newSpan
//        val last = existing.maxBy { it.end } ?: newSpan
//
//        if (first.start == newSpan.end) {
//            first.start = newSpan.start
//            updateText()
//            return
//        }
//
//        if (last.end == newSpan.start) {
//            last.end = newSpan.end
//            updateText()
//            return
//        }
//
//        removeSpans(existing)
//
//        if (first.end == newSpan.start && last.start == newSpan.end && last.span != first.span) {
//            attachSpan(newSpan.copy(start = first.start, end = last.end, span = newSpan.span.copy()))
//            updateText()
//            return
//        }
//
//        if (first.start < newSpan.start) {
//            attachSpan(newSpan.copy(start = first.start, end = newSpan.start, span = newSpan.span.copy()))
//        }
//
//        if (last.end > newSpan.end) {
//            attachSpan(newSpan.copy(start = newSpan.end, end = last.end, span = newSpan.span.copy()))
//        }
//    }
//

//    private fun attachSpan(newSpan: SpanHolder) {
//        spans.add(newSpan)
//        updateText()
//    }
//
//    private fun removeSpans(spans: List<SpanHolder>) {
//        this.spans.removeAll(spans)
//        updateText()
//    }

    override fun afterTextChanged(spannable: Editable) {
    }

    override fun beforeTextChanged(t: CharSequence?, changeStart: Int, before: Int, count: Int) {
    }

    override fun onTextChanged(t: CharSequence?, s: Int, before: Int, count: Int) {
        val added = addedCharacters(count, before)
        val changeStart = s + before
        val changeEnd = changeStart + added

        if (added > 0) {
            val prevStyles = getSpans(changeStart)
            val nextStyles = getSpansAfter(changeStart)
            val keep = prevStyles.filter { currentState.containsType(it.third) }
            val drop = prevStyles.filter { !currentState.containsType(it.third) }
            val add = currentState.filter { c -> !prevStyles.map { it.third }.containsType(c) }

            keep.forEach { (start, end, span) ->

                val newStart = if (start >= changeStart) start + added else start
                val newEnd = if (end >= changeStart) end + added else end

                currentSpans.removeAll { it.third == span }
                currentSpans.add(Triple(newStart, newEnd, span))
                text.setSpan(span, newStart, newEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            }

            add.forEach { span ->
                currentSpans.add(Triple(changeStart, changeEnd, span))
                text.setSpan(span, changeStart, changeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            Log.d("KEEP", keep.toString())
            Log.d("ADD", add.toString())
            Log.d("DROP", drop.toString())
            Log.d("CURRENT SPANS", currentSpans.toString())

        }
//
//        //TODO CHECK Change
//        val lowerBound = min(changeStart, changeEnd)
//        val higherBound = max(changeStart, changeEnd)
//        val currentSpans = getSpans(lowerBound).map { it.third }
//        if (added > 0) {
//            applyState(changeStart, changeEnd, currentState)
////            val toRemove = currentSpans.filter { !currentState.containsType(it) }
////            val toAdd = currentState.filter { !currentSpans.containsType(it) }
////
////            removeAllSpans(toRemove)
////            toAdd.forEach { createOrUpdate(lowerBound, higherBound, it) }
//        }
////        deleteRedundantSpans()
//
//        val toUpdate = getSpansAfter(changeEnd).map { it.third }
//        toUpdate.forEach { span ->
//            var (start, end) = getSpanBounds(span)
//
//            if (span is InPlaceSpan && start >= changeStart) {
//                addSpan(span, start + added, end + added)
//            } else {
//                if (start >= changeStart) {
//                    start += added
//                }
//
//                if (end >= changeStart) {
//                    end += added
//                }
//                createOrUpdate(start, end, span)
//            }
//        }
//
////        updateExisting(changeStart, added)
////        addNewStyles(changeStart, changeEnd)
    }

    private fun getSpansAfter(bound: Int): List<Triple<Int, Int, Span>> {
        return currentSpans.filter { (start, end, _) ->
            start >= bound
        }
    }

    private fun applyState(start: Int, end: Int, state: List<Span>) {
        state.forEach { createOrUpdate(start, end, it) }
    }

    private fun deleteRedundantSpans() {
        removeAllSpans(currentSpans.map { it.third }.filter { !currentState.containsType(it) })
    }

    private fun getSpanBounds(span: Span): Pair<Int, Int> {
        return currentSpans.find { it.third == span }?.let { it.first to it.second }
                ?: let { -1 to -1 }
    }

    private fun getSpans(sStart: Int, sEnd: Int = sStart): List<Triple<Int, Int, Span>> {
        val (s, e) = when {
            sStart < 0 -> return listOf()
            sStart == sEnd && sStart == 0 -> return listOf()
            sStart == sEnd -> sStart - 1 to sStart
            else -> sStart to (sEnd - 1)
        }
        return currentSpans.filter { (start, end, _) ->
            start in s..e || end in s..e || s in start..end || e in start..end
        }
    }

    private fun addSpan(span: Span, start: Int, end: Int) {
        currentSpans.removeAll { it.third == span }
        if (end > start && start < text.length) {
            currentSpans.add(Triple(start, end, span))
            text.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun removeTextSpan(span: Span) {
        currentSpans.removeAll { it.third == span }
        text.removeSpan(span)
    }

    private fun removeAllSpans(spans: List<Span>) {
        spans.forEach { removeTextSpan(it) }
    }

    fun addState(span: Span) {
        if (!currentState.containsType(span)) {
            setStates(currentState + span)
        }
    }

    fun removeState(span: Span) {
        if (currentState.containsType(span)) {
            setStates(currentState.filter { it::class.java != span::class.java })
        }
    }

    fun setStates(spans: List<Span>) {

        if (selectionStart != selectionEnd) {
            updateStates(selectionStart, selectionEnd, spans)
        }
        currentState = spans
    }

    private fun updateStates(start: Int, end: Int, span: List<Span>) {
        span.forEach { createOrUpdate(start, end, it) }
    }

    private fun createOrUpdate(start: Int, end: Int, span: Span) {

        val existing: List<Triple<Int, Int, Span>> = getSpans(start, end).filter { it.third::class.java == span::class.java }

        if (span is InPlaceSpan) {
            currentState = currentState - span
            removeText(start, end)
            addPlaceholder(start)
            addSpan(span, start, start + 1)
        }

        if (existing.isEmpty()) {
            addSpan(span, start, end)
            return
        }

        val (firstStart, firstEnd, first) = existing.minBy { it.first }
                ?: Triple(start, end, span)
        val (lastStart, lastEnd, last) = existing.minBy { it.first }
                ?: Triple(start, end, span)

        if (firstStart == end) {
            addSpan(first, start, firstEnd)
            return
        }

        if (lastEnd == start) {
            addSpan(last, lastStart, end)
            return
        }

        removeAllSpans(existing.map { it.third })

        if (firstEnd == start && lastStart == end && last != first) {
            addSpan(span.copy(), firstStart, lastEnd)
            return
        }

        if (firstStart < start) {
            addSpan(span.copy(), firstStart, start)
        }

        if (lastEnd > end) {
            addSpan(span.copy(), end, lastEnd)
        }
    }

    private fun addPlaceholder(newStart: Int) {
        //TODO CHECK
        editor.text = editor.text.insert(newStart, PLACEHOLDER)
    }

    private fun removeText(newStart: Int, newEnd: Int) {
        //TODO CHECK
        editor.setText(editor.text.removeRange(newStart, newEnd))
    }

//    private fun addNewStyles(changeStart: Int, changeEnd: Int) {
//        clicked.forEach { setSpan(changeStart, changeEnd, it) }
//        clicked.clear()
//    }
//
//    private fun updateExisting(start: Int, added: Int) {
//        // start after -> start & end + added
//        // end after -> end + added
//        val toRemove = spans.mapNotNull {
//
//
//            if (it.span is InPlaceSpan) {
//                if (added < 0 && start + added < it.end && start >= it.end) {
//                    return@mapNotNull it
//                }
//                if (it.start >= start) {
//                    it.start += added
//                    it.end += added
//                }
//                return@mapNotNull null
//            }
//
//            if (it.start >= start) {
//                it.start += added
//            }
//
//            if (it.end >= start) {
//                it.end += added
//            }
//
//            if (it.end <= it.start) {
//                return@mapNotNull it
//            }
//            null
//        }
//        spans.removeAll(toRemove)
//
//        updateText()
//    }
//
//    private fun updateText() {
//        val invalidSpans = spans.filter { it.start >= text.length }
//        spans.removeAll(invalidSpans)
//        text.getSpans(0, text.length, Span::class.java)
//                .filter { tSpan -> spans.find { it.span == tSpan } == null }
//                .forEach { text.removeSpan(it) }
//        spans.forEach { text.setSpan(it.span, it.start, it.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
//    }

    private fun addedCharacters(count: Int, before: Int) = count - before

//    fun clearClicked() {
//        clicked.clear()
//    }

    companion object {
        private const val PLACEHOLDER = "O"
        //        private const val PLACEHOLDER = "\u2610"
        fun forEditor(editor: EditText) = SpanManager(editor).also {
            editor.addTextChangedListener(it)
        }
    }

}

private fun List<Any>?.containsType(obj: Any) = this != null && find { it::class.java == obj::class.java } != null

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