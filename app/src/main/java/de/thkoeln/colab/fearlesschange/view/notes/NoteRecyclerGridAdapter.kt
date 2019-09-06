package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.RecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import kotlinx.android.synthetic.main.note_grid_item.view.*
import kotlinx.android.synthetic.main.note_label_item.view.*
import kotlinx.android.synthetic.main.note_todo_item.view.*

class NoteLabelRecyclerAdapter : RecyclerViewAdapter<Label, NoteLabelRecyclerAdapter.NoteLabelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteLabelViewHolder {
        return NoteLabelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_label_item, parent, false))
    }

    class NoteLabelViewHolder(itemView: View) : ViewHolder<Label>(itemView) {
        override fun bind(item: Label) {
            itemView.note_label_item_chip.color = item.color
            itemView.note_label_item_chip.name = item.name
        }
    }
}

class NoteTodoRecyclerAdapter(private val updateTodo: UpdateTodo) : RecyclerViewAdapter<Todo, NoteTodoRecyclerAdapter.NoteTodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteTodoViewHolder {
        return NoteTodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_todo_item, parent, false), updateTodo)
    }

    class NoteTodoViewHolder(itemView: View, private val updateTodo: UpdateTodo) : ViewHolder<Todo>(itemView) {
        override fun bind(item: Todo) {
            itemView.note_todo_item_check.isChecked = item.state
            itemView.note_todo_item_check.text = item.text
            itemView.note_todo_item_check.setOnCheckedChangeListener { _, isChecked -> updateTodo(item, isChecked) }
        }
    }
}

//typealias FetchNoteLabels = (note: Note, callback: (List<Label>) -> Unit) -> Unit
//typealias FetchNoteTodos = (note: Note, callback: (List<Todo>) -> Unit) -> Unit
typealias UpdateTodo = (todo: Todo, newState: Boolean) -> Unit

class NoteRecyclerGridAdapter(context: Context, private val updateTodo: UpdateTodo) : SwipeToDeleteRecyclerViewAdapter<PatternNoteData, NoteRecyclerGridAdapter.NoteViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_grid_item, parent, false), updateTodo)

    class NoteViewHolder(itemView: View, updateTodo: UpdateTodo) : ViewHolder<PatternNoteData>(itemView) {
        private val labelAdapter = NoteLabelRecyclerAdapter()
        private val todoAdapter = NoteTodoRecyclerAdapter(updateTodo)

        init {
            itemView.note_grid_item_label_container.layoutManager = FlexboxLayoutManager(itemView.context, FlexDirection.ROW)
            itemView.note_grid_item_label_container.adapter = labelAdapter

            itemView.note_grid_item_todo_container.layoutManager = LinearLayoutManager(itemView.context)
            itemView.note_grid_item_todo_container.adapter = todoAdapter
        }

        override fun bind(item: PatternNoteData) {
            labelAdapter.setItems(item.labels)
            todoAdapter.setItems(item.todos)
            itemView.note_grid_item_note_text.loadData(item.note.text, "text/html", "UTF8")
        }
    }
}