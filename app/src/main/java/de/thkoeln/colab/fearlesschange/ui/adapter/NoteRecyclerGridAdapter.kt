package de.thkoeln.colab.fearlesschange.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.note.Note
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.ui.SwipeToDeleteRecyclerViewHolder
import kotlinx.android.synthetic.main.note_grid_item.view.*

/**
 *
 * Adapter to adapt a notes to a recycler view
 *
 * @author Florian Herborn on 10.08.2018.
 * @since 0.0.1
 * @property notes notes to show
 * @see RecyclerView.Adapter
 */
class NoteRecyclerGridAdapter(context: Context, var notes: List<Note> = listOf()) : SwipeToDeleteRecyclerViewAdapter<Note, NoteRecyclerGridAdapter.NoteViewHolder>(context) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_grid_item, parent, false)
        return NoteViewHolder(view)
    }


    class NoteViewHolder(itemView: View) : SwipeToDeleteRecyclerViewHolder<Note>(itemView) {

        private var noteTitleView = itemView.findViewById<TextView>(R.id.create_note_note_title)
        private var noteDescriptionView = itemView.findViewById<TextView>(R.id.create_note_note_text)

        override fun bind(item: Note) {
            noteTitleView.text = item.title
            noteDescriptionView.text = item.text
        }

        override fun getDisplayName(item: Note) = item.title
        override fun getForeground() = itemView.note_item_foreground
    }
}