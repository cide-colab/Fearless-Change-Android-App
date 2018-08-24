package de.thkoeln.fherborn.fearlesschange.v2.ui.notes


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.Note
import de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel.NoteViewModel
import de.thkoeln.fherborn.fearlesschange.v2.helper.SwipeCallback
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedBackground
import de.thkoeln.fherborn.fearlesschange.v2.ui.adapter.NoteRecyclerGridAdapter
import kotlinx.android.synthetic.main.fragment_pattern_notes.*


class PatternNotesFragment : Fragment() {

    private var patternId: Long = -1L
    private lateinit var viewModel: NoteViewModel
    private val adapter = NoteRecyclerGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        patternId = arguments?.getLong(CARD_ID_KEY) ?: throw IllegalArgumentException("Missing patternId")
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) {
            Snackbar.make(pattern_notes_container, it.message, it.duration).show()
        }

        viewModel.openCreateNoteDialogEvent.observe(this, Observer {
            openCreateNoteDialog()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_pattern_notes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pattern_notes_container.setOptimizedBackground(R.drawable.notes_bg)
        initNotes()
        pattern_notes_add_note_btn.setOnClickListener {
            viewModel.addNoteButtonClicked(patternId)
        }
    }


    private fun initNotes() {
        pattern_notes_recycler_view.adapter = adapter
        viewModel.getNotesForPattern(patternId).nonNullObserve(this) {
            updateNotes(it)
        }
        val touchCallback = SwipeCallback(ItemTouchHelper.LEFT) { viewHolder, _ ->
            viewHolder?.adapterPosition?.let {
                viewModel.deleteNoteConfirmed(adapter.notes[it])
            }
        }
        ItemTouchHelper(touchCallback).attachToRecyclerView(pattern_notes_recycler_view)
    }

    private fun updateNotes(it: List<Note>) {
        adapter.notes = it
        adapter.notifyDataSetChanged()
    }

    private fun openCreateNoteDialog() {
        val dialog = CreateNoteDialog(context)
        dialog.onConfirmListener = { title, description ->
            viewModel.createNoteConfirmed(patternId, title, description)
        }
        dialog.show()
    }

    companion object {
        private const val CARD_ID_KEY = "card_id"
        fun newInstance(cardId: Long) =
                PatternNotesFragment().apply {
                    arguments = Bundle().apply {
                        putLong(CARD_ID_KEY, cardId)
                    }
                }
    }

}
