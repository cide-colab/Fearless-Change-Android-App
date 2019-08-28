package de.thkoeln.colab.fearlesschange.view.notes

import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.layout.LayoutViewHolder
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import kotlinx.android.synthetic.main.note_grid_item.view.*


class NoteGridItemViewHolder : LayoutViewHolder<Note>(R.layout.note_grid_item) {
    override fun bind(view: View, value: Note) {
        view.note_text.text = value.text
    }
}
