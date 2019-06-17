package de.thkoeln.colab.fearlesschange.ui.notes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.observe
import de.thkoeln.colab.fearlesschange.ui.BasicPatternFragment
import de.thkoeln.colab.fearlesschange.ui.adapter.NoteRecyclerGridAdapter
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

