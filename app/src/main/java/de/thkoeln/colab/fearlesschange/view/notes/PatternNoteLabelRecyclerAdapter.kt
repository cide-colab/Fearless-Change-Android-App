package de.thkoeln.colab.fearlesschange.view.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.RecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import kotlinx.android.synthetic.main.note_label_item.view.*
import kotlinx.android.synthetic.main.note_todo_item.view.*

class PatternNoteLabelRecyclerAdapter(swipeToDeleteEnabled: Boolean = false) : RecyclerViewAdapter<Label, PatternNoteLabelRecyclerAdapter.NoteLabelViewHolder>(swipeToDeleteEnabled) {

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

class PatternNoteTodoRecyclerAdapter(var onTodoChanged: OnTodoChanged = { _, _ -> }) : RecyclerViewAdapter<Todo, PatternNoteTodoRecyclerAdapter.NoteTodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteTodoViewHolder {
        return NoteTodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_todo_item, parent, false), onTodoChanged)
    }

    class NoteTodoViewHolder(itemView: View, private val onTodoChanged: OnTodoChanged) : ViewHolder<Todo>(itemView) {
        override fun bind(item: Todo) {
            itemView.note_todo_item_check.isChecked = item.state
            itemView.note_todo_item_check.text = item.text
            itemView.note_todo_item_check.setOnCheckedChangeListener { _, isChecked -> onTodoChanged(item, isChecked) }
        }
    }
}