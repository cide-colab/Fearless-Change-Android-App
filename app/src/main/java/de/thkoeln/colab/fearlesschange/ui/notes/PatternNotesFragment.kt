package de.thkoeln.colab.fearlesschange.ui.notes


import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.note.Note
import de.thkoeln.colab.fearlesschange.data.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.helper.events.SingleActionLiveData
import de.thkoeln.colab.fearlesschange.observe
import de.thkoeln.colab.fearlesschange.ui.adapter.NoteRecyclerGridAdapter
import de.thkoeln.colab.fearlesschange.ui.plugins.BasicPatternFragment
import de.thkoeln.colab.fearlesschange.ui.plugins.BasicPatternViewModel
import kotlinx.android.synthetic.main.fragment_pattern_notes.*


class PatternNotesFragment : BasicPatternFragment<PatternNotesViewModel>() {

    private val args: PatternNotesFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_pattern_notes, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val adapter = NoteRecyclerGridAdapter(requireContext())
        adapter.onItemDeletedListener = viewModel.onItemDeleteListener
        pattern_notes_recycler_view.adapter = adapter

        viewModel.createDialogEvent.observe(this) { openCreateNoteDialog() }
        viewModel.notes.observe(this) { adapter.setItems(it) }

        pattern_notes_add_note_btn.setOnClickListener { viewModel.addNoteButtonClicked() }
    }


    private fun openCreateNoteDialog() {
        val dialog = CreateNoteDialog(requireContext())
        dialog.onConfirmListener = viewModel.createNoteConfirmed
        dialog.show()
    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternNotesViewModelFactory(requireActivity().application, args)).get(PatternNotesViewModel::class.java)

    companion object {
        fun newInstance(patternId: Long) = PatternNotesFragment().apply {
            arguments = PatternNotesFragmentArgs(patternId).toBundle()
        }

    }
}

class PatternNotesViewModel(application: Application, args: PatternNotesFragmentArgs) : BasicPatternViewModel(application) {
    val createNoteConfirmed: (title: String, description: String) -> Unit = { title, description ->
        noteRepository.insert(Note(title = title, text = description, patternId = args.patternId))
    }

    private val noteRepository = NoteRepository(application)

    val addNoteButtonClicked: () -> Unit = { createDialogEvent.invoke(args.patternId) }
    val onItemDeleteListener: (item: Note) -> Unit = { noteRepository.delete(it) }

    val createDialogEvent = SingleActionLiveData<Long>()
    val notes = noteRepository.getNotesForPattern(args.patternId)

}


@Suppress("UNCHECKED_CAST")
class PatternNotesViewModelFactory(private val application: Application, private val args: PatternNotesFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternNotesViewModel(application, args) as T
    }
}