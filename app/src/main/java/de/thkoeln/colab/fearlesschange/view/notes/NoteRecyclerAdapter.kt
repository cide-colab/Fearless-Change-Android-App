package de.thkoeln.colab.fearlesschange.view.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.RecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import kotlinx.android.synthetic.main.note_item.view.*


typealias OnTodoChanged = (todo: Todo, newState: Boolean) -> Unit

class NoteRecyclerAdapter : RecyclerViewAdapter<NoteData, NoteRecyclerAdapter.NoteViewHolder>(true) {

    var onTodoChanged: OnTodoChanged = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false), onTodoChanged)

    class NoteViewHolder(itemView: View, onTodoChanged: OnTodoChanged) : ViewHolder<NoteData>(itemView) {

        init {
            itemView.note_item_note_card.onTotoChanged = onTodoChanged
        }

        override fun bind(item: NoteData) {
            itemView.note_item_note_card.noteData = item
        }
    }
}