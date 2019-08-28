package de.thkoeln.colab.fearlesschange.view.notes

import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.layout.LayoutViewHolder
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import kotlinx.android.synthetic.main.note_grid_item.view.*


class NoteGridItemViewHolder : LayoutViewHolder<Note>(R.layout.note_grid_item) {
    override fun bind(view: View, value: Note) {

        Log.d("HTML: ", value.text)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.note_text.text = Html.fromHtml(value.text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            view.note_text.text = Html.fromHtml(value.text)
        }

    }
}
