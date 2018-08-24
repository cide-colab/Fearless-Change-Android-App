package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.Note

/**
 *
 * Adapter to adapt a notes to a recycler view
 *
 * @author Florian Herborn on 10.08.2018.
 * @since 0.0.1
 * @property notes notes to show
 * @see RecyclerView.Adapter
 */
class NoteRecyclerGridAdapter(var notes: List<Note> = listOf()) : RecyclerView.Adapter<NoteRecyclerGridAdapter.NoteViewHolder>() {


    fun updateNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }
    /**
     * Inflates ItemView and creates a ViewHolder with this view
     * @see RecyclerView.Adapter
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRecyclerGridAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_note_item, parent, false)
        return NoteViewHolder(view)
    }

    /**
     * Binds a viewholder to
     * @see RecyclerView.Adapter
     */
    override fun onBindViewHolder(holder: NoteRecyclerGridAdapter.NoteViewHolder, position: Int) {
        holder.bindCard(notes[position])
    }

    /**
     * returns the size of the noteList
     * @see RecyclerView.Adapter
     */
    override fun getItemCount(): Int {
        return notes.size
    }

    /**
     * @see RecyclerView.ViewHolder
     * @property noteDescriptionView textview to show note description
     * @property noteTitleView textview to show note title
     */
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var noteTitleView = itemView.findViewById<TextView>(R.id.create_note_note_title)
        private var noteDescriptionView = itemView.findViewById<TextView>(R.id.create_note_note_text)

        fun bindCard(note: Note) {
            noteTitleView.text = note.title
            noteDescriptionView.text = note.text
        }
    }
}