package de.thkoeln.colab.fearlesschange.view.notes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.FinishJobOnDestroy
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import kotlinx.android.synthetic.main.pattern_notes_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PatternNotesFragment : InteractiveFragment<PatternNotesViewModel>() {

    private val args: PatternNotesFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        lifecycle.addObserver(FinishJobOnDestroy())
        return inflater.inflate(R.layout.pattern_notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val adapter = NoteRecyclerAdapter()
        adapter.onTodoChanged = onTodoChanged
        adapter.afterDeleteItemListener = { item, index ->
            val duration = 1500
            val job = lifecycleScope.launch {
                delay(duration.toLong())
                viewModel.deleteNote(item.note)
            }
            Snackbar.make(pattern_notes_container, R.string.message_note_deleted, Snackbar.LENGTH_LONG)
                    .setDuration(duration)
                    .setAction(R.string.action_undo) {
                        job.cancel()
                        adapter.restoreItem(item, index)
                    }
                    .show()
        }
        pattern_notes_recycler_view.adapter = adapter

        viewModel.loadNotes { adapter.setItems(it) }

        pattern_notes_notes_create_note.setOnClickListener { viewModel.createNoteButtonClicked() }

    }

    private val onTodoChanged: OnTodoChanged = { todo: Todo, state: Boolean ->
        viewModel.updateTodo(todo, state)
    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternNotesViewModelFactory(requireActivity().application, args)).get(PatternNotesViewModel::class.java)

    companion object {
        fun newInstance(patternId: Long) = PatternNotesFragment().apply {
            arguments = PatternNotesFragmentArgs(patternId).toBundle()
        }

    }
}

