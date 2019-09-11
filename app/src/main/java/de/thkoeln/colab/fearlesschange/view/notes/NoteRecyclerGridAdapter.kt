package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.core.getDrawable
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import kotlinx.android.synthetic.main.note_item.view.*

data class NoteData(val pattern: Pattern, val note: PatternNoteData)

class NoteRecyclerGridAdapter(context: Context, private val updateTodo: UpdateTodo, private val patternClicked: (pattern: Pattern) -> Unit) : SwipeToDeleteRecyclerViewAdapter<NoteData, NoteRecyclerGridAdapter.NoteViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false), updateTodo, patternClicked)

    class NoteViewHolder(itemView: View, updateTodo: UpdateTodo, private val patternClicked: (pattern: Pattern) -> Unit) : ViewHolder<NoteData>(itemView) {
        private val labelAdapter = PatternNoteLabelRecyclerAdapter()
        private val todoAdapter = PatternNoteTodoRecyclerAdapter(updateTodo)

        init {
            itemView.note_item_note_label_container.layoutManager = FlexboxLayoutManager(itemView.context, FlexDirection.ROW)
            itemView.note_item_note_label_container.adapter = labelAdapter

            itemView.note_item_note_todo_container.layoutManager = LinearLayoutManager(itemView.context)
            itemView.note_item_note_todo_container.adapter = todoAdapter

            itemView.note_item_note_text.setEditorFontColor(itemView.resources.getColor(R.color.primaryText))
        }

        override fun bind(item: NoteData) {
            labelAdapter.setItems(item.note.labels)
            todoAdapter.setItems(item.note.todos)
            itemView.note_item_note_text.html = item.note.note.text


            with(item.pattern) {
                val picture = itemView.context.getDrawable(pictureName)
                        ?: R.drawable.default_pattern_image
                itemView.note_item_pattern_image.setImageResource(picture)
                itemView.note_item_pattern_title.text = title
                itemView.note_item_pattern_summary.text = summary
                itemView.note_item_pattern_cardview.setOnClickListener { patternClicked(this) }
//                itemView.note_item_pattern_cardview.setOnClickListener { patternClickedListener(value) }
            }
        }
    }
}