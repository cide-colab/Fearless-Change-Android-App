package de.thkoeln.fherborn.fearlesschange.adapters

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note


class NoteRecyclerGridAdapter(var notes: List<Note> = listOf()) : RecyclerView.Adapter<NoteRecyclerGridAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRecyclerGridAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_note_item, parent, false)
        return NoteViewHolder(view, Typeface.createFromAsset(parent.context.assets, "fonts/note_font.ttf"))
    }

    override fun onBindViewHolder(holder: NoteRecyclerGridAdapter.NoteViewHolder, position: Int) {
        holder.bindCard(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(itemView: View, font: Typeface) : RecyclerView.ViewHolder(itemView) {

        private var noteTitleView = itemView.findViewById<TextView>(R.id.note_title).apply { typeface = font }
        private var noteDescriptionView = itemView.findViewById<TextView>(R.id.note_description).apply { typeface = font }

        fun bindCard(note: Note) {
            noteTitleView.text = note.title
            noteDescriptionView.text = note.description
        }
    }
}