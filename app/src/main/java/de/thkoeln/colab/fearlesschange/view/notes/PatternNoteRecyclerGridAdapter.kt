package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import kotlinx.android.synthetic.main.pattern_note_item.view.*


typealias UpdateTodo = (todo: Todo, newState: Boolean) -> Unit

class PatternNoteRecyclerGridAdapter(context: Context, private val updateTodo: UpdateTodo) : SwipeToDeleteRecyclerViewAdapter<PatternNoteData, PatternNoteRecyclerGridAdapter.NoteViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pattern_note_item, parent, false), updateTodo)

    class NoteViewHolder(itemView: View, updateTodo: UpdateTodo) : ViewHolder<PatternNoteData>(itemView) {
        private val labelAdapter = PatternNoteLabelRecyclerAdapter()
        private val todoAdapter = PatternNoteTodoRecyclerAdapter(updateTodo)

        init {
            itemView.pattern_note_item_label_container.layoutManager = FlexboxLayoutManager(itemView.context, FlexDirection.ROW)
            itemView.pattern_note_item_label_container.adapter = labelAdapter

            itemView.pattern_note_item_todo_container.layoutManager = LinearLayoutManager(itemView.context)
            itemView.pattern_note_item_todo_container.adapter = todoAdapter

            itemView.pattern_note_item_note_text.setEditorFontColor(itemView.resources.getColor(R.color.primaryText))
        }

        override fun bind(item: PatternNoteData) {
            labelAdapter.setItems(item.labels)
            todoAdapter.setItems(item.todos)
            itemView.pattern_note_item_note_text.html = item.note.text
        }
    }
}