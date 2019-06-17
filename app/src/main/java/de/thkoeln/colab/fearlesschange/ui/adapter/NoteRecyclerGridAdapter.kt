package de.thkoeln.colab.fearlesschange.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.note.Note
import de.thkoeln.colab.fearlesschange.ui.LayoutAdapter
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewHolder
import kotlinx.android.synthetic.main.note_grid_item.view.*
import kotlinx.android.synthetic.main.swipe_to_delete_wrapper.view.*


class NoteGridItemAdapter : LayoutAdapter<Note>(R.layout.note_grid_item) {
    override fun bind(view: View, value: Note) {
        view.create_note_note_title.text = value.title
        view.create_note_note_text.text = value.text
    }
}

class NoteRecyclerGridAdapter(context: Context, var notes: List<Note> = listOf()) : SwipeToDeleteRecyclerViewAdapter<Note, NoteRecyclerGridAdapter.NoteViewHolder>(context) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_to_delete_wrapper, parent, false)
        return NoteViewHolder(view)
    }


    class NoteViewHolder(itemView: View) : SwipeToDeleteRecyclerViewHolder<Note>(itemView) {
        private val adapter = NoteGridItemAdapter().apply {
            inflate(itemView.swipe_to_delete_container, true)
        }

        override fun bind(item: Note) {
            adapter.bind(item)
        }

        override fun getDisplayName(item: Note) = item.title
        override fun getForeground() = itemView.swipe_to_delete_container
    }
}