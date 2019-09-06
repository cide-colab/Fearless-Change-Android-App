package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import kotlinx.android.synthetic.main.note_grid_item.view.*


class NoteRecyclerGridAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<Note, NoteRecyclerGridAdapter.NoteViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_grid_item, parent, false)
        return NoteViewHolder(view)
    }

    class NoteViewHolder(itemView: View) : ViewHolder<Note>(itemView) {
        override fun bind(item: Note) {
            itemView.note_text.loadData(item.text, "text/html", "UTF8")
        }
    }
}