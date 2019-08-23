package de.thkoeln.colab.fearlesschange.view.notes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.observe
import de.thkoeln.colab.fearlesschange.core.pattern.PatternViewModelFragment
import kotlinx.android.synthetic.main.pattern_notes_fragment.*


class PatternNotesFragment : PatternViewModelFragment<PatternNotesViewModel>() {

    private val args: PatternNotesFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.pattern_notes_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val adapter = NoteRecyclerGridAdapter(requireContext())
        adapter.onDeleteItemAcceptedListener = viewModel.onItemDeleteListener
        adapter.onDeleteSnackBarText = { getString(R.string.message_note_deleted, it.title) }
        adapter.onDeleteUndoActionText = { getString(R.string.action_undo) }
        pattern_notes_recycler_view.adapter = adapter

        viewModel.createDialogEvent.observe(this) { openCreateNoteDialog() }
        viewModel.notes.observe(this) { adapter.setItems(it) }

        pattern_notes_create_note.setOnClickListener { viewModel.createNoteButtonClicked() }

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

