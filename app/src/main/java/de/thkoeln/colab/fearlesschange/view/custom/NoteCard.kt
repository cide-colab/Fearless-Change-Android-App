package de.thkoeln.colab.fearlesschange.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.view.notes.NoteData
import de.thkoeln.colab.fearlesschange.view.notes.OnTodoChanged
import de.thkoeln.colab.fearlesschange.view.notes.PatternNoteLabelRecyclerAdapter
import de.thkoeln.colab.fearlesschange.view.notes.PatternNoteTodoRecyclerAdapter
import kotlinx.android.synthetic.main.note_card.view.*

class NoteCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val labelAdapter = PatternNoteLabelRecyclerAdapter()
    private val todoAdapter = PatternNoteTodoRecyclerAdapter()

    var onTotoChanged: OnTodoChanged
        get() = todoAdapter.onTodoChanged
        set(value) {
            todoAdapter.onTodoChanged = value
        }


    var noteData: NoteData? = null
        set(value) {
            field = value
            applyAttributes(field)
        }

    init {
        inflate(context, R.layout.note_card, this)
        note_card_label_container.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW)
        note_card_label_container.adapter = labelAdapter

        note_card_todo_container.layoutManager = LinearLayoutManager(context)
        note_card_todo_container.adapter = todoAdapter

        note_card_text.setEditorFontColor(resources.getColor(R.color.colorOnSurface))
        note_card_text.isFocusable = false
        note_card_text.isClickable = true
    }

    private fun applyAttributes(noteData: NoteData?) {

        labelAdapter.setItems(noteData?.labels ?: listOf())
        todoAdapter.setItems(noteData?.todos ?: listOf())
        note_card_text.html = noteData?.note?.text

        invalidate()
        requestLayout()
    }
}