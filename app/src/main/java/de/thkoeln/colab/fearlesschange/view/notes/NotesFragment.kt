package de.thkoeln.colab.fearlesschange.view.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import de.thkoeln.colab.fearlesschange.view.custom.MarginItemDecoration
import kotlinx.android.synthetic.main.notes_fragment.*

class NotesFragment : InteractiveFragment<NotesViewModel>() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = NoteRecyclerViewAdapter()
        adapter.onTodoChanged = viewModel.updateTodo
        adapter.patternClicked = viewModel.patternClicked

        adapter.afterDeleteItemListener = { item, index ->
            viewModel.deleteNote(item.noteData.note)
            Snackbar.make(notes_fragment_recycler_view, R.string.message_note_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_undo) {
                        viewModel.addNote(item.noteData.note)
                        adapter.restoreItem(item, index)
                    }
                    .show()
        }


        notes_fragment_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        notes_fragment_recycler_view.adapter = adapter
        notes_fragment_recycler_view.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.default_padding).toInt()))

        viewModel.getNoteData { adapter.setItems(it) }

    }

    override fun createViewModel() = ViewModelProviders.of(this).get(NotesViewModel::class.java)

}