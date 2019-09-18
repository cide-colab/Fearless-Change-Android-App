package de.thkoeln.colab.fearlesschange.view.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.RecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import kotlinx.android.synthetic.main.note_pattern_item.view.*

data class PatternNoteData(val patternPreviewData: PatternPreviewData, val noteData: NoteData)

class NoteRecyclerViewAdapter : RecyclerViewAdapter<PatternNoteData, NoteRecyclerViewAdapter.NoteViewHolder>(true) {

    var onTodoChanged: OnTodoChanged = { _, _ -> }
    var patternClicked: (patternData: PatternPreviewData) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_pattern_item, parent, false), onTodoChanged, patternClicked)

    class NoteViewHolder(itemView: View, private val onTodoChanged: OnTodoChanged, private val patternClicked: (patternData: PatternPreviewData) -> Unit) : ViewHolder<PatternNoteData>(itemView) {

        init {
            itemView.note_pattern_item_note_card.onTotoChanged = onTodoChanged
            itemView.note_pattern_item_pattern_card.apply {
                setOnClickListener { patternPreviewData?.let { patternClicked(it) } }
            }
        }

        override fun bind(item: PatternNoteData) {
            itemView.note_pattern_item_note_card.noteData = item.noteData
            itemView.note_pattern_item_pattern_card.patternPreviewData = item.patternPreviewData
        }
    }
}