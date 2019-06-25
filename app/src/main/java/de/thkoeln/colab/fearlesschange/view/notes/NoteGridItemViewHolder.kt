package de.thkoeln.colab.fearlesschange.view.notes

import android.view.View
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.layout.LayoutViewHolder
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import kotlinx.android.synthetic.main.note_grid_item.view.*


class NoteGridItemViewHolder : LayoutViewHolder<Note>(R.layout.note_grid_item) {
    override fun bind(view: View, value: Note) {
        view.create_note_note_title.text = value.title
        view.create_note_note_text.text = value.text
    }
}
