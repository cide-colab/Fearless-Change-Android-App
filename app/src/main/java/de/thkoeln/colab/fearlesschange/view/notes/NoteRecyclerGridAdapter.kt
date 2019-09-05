package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.adapters.SwipeToDeleteRecyclerViewAdapter
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import kotlinx.android.synthetic.main.swipe_to_delete_wrapper.view.*


class NoteRecyclerGridAdapter(context: Context) : SwipeToDeleteRecyclerViewAdapter<Note, NoteRecyclerGridAdapter.NoteViewHolder>(context) {

//    var onSpansChangedListener: (note: Note, text: String) -> Unit = {_,_ ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_to_delete_wrapper, parent, false)
        return NoteViewHolder(view/*, onSpansChangedListener*/)
    }


    class NoteViewHolder(itemView: View/*, onSpanChangedListener: (note: Note, text: String) -> Unit*/) : SwipeToDeleteRecyclerViewHolder<Note>(itemView) {
        private val adapter = NoteGridItemViewHolder(/*onSpansChangedListener*/).apply {
            inflate(itemView.swipe_to_delete_container, true)
        }

        override fun bind(item: Note) {
            adapter.bind(item)
        }
        override fun getForeground() = itemView.swipe_to_delete_container
    }
}