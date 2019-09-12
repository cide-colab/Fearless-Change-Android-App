package de.thkoeln.colab.fearlesschange.view.notes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.pattern.PatternViewModelFragment
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import kotlinx.android.synthetic.main.pattern_notes_fragment.*


class PatternNotesFragment : PatternViewModelFragment<PatternNotesViewModel>() {

    private val args: PatternNotesFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.pattern_notes_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val adapter = PatternNoteRecyclerGridAdapter(requireContext(), updateTodo)
        adapter.afterDeleteItemListener = { item, index ->
            viewModel.deleteNote(item.note)
            Snackbar.make(pattern_notes_container, R.string.message_note_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_undo) {
                        viewModel.addNote(item.note)
                        adapter.restoreItem(item, index)
                    }
                    .show()
        }
        pattern_notes_recycler_view.adapter = adapter

        viewModel.loadNotes { adapter.setItems(it) }

        pattern_notes_create_note.setOnClickListener { viewModel.createNoteButtonClicked() }

    }

    private val updateTodo: UpdateTodo = { todo: Todo, state: Boolean ->
        viewModel.updateTodo(todo, state)
    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternNotesViewModelFactory(requireActivity().application, args)).get(PatternNotesViewModel::class.java)

    companion object {
        fun newInstance(patternId: Long) = PatternNotesFragment().apply {
            arguments = PatternNotesFragmentArgs(patternId).toBundle()
        }

    }
}

