package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.NoteRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.NoteInputDialog
import kotlinx.android.synthetic.main.fragment_card_notes.*


class CardNotesFragment : Fragment() {

    private lateinit var noteRepository: NoteRepository
    private val notesAdapter = NoteRecyclerGridAdapter()
    var cardId: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_card_notes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardId = arguments?.getLong(CARD_ID_KEY)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        noteRepository = NoteRepository(context)
        setUpNotes()
        setListeners()
    }

    private fun setUpNotes() {
        notes_recycler_view.adapter = notesAdapter
        notes_recycler_view.isNestedScrollingEnabled = false
        notes_recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        cardId?.let {
            noteRepository.getByCardId(it).observe(activity as AppCompatActivity, Observer {
                notesAdapter.notes = it ?: listOf()
                notesAdapter.notifyDataSetChanged()
            })
        }
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                when (swipeDir) {
                    ItemTouchHelper.LEFT -> removeNote(notesAdapter.notes[viewHolder.adapterPosition])
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(notes_recycler_view)
    }

    private fun addNoteClicked() {
        val inputDialog = NoteInputDialog(context)
        inputDialog.onConfirmListener = { title, description ->
            cardId?.let {
                val note = Note(title = title, description = description, cardId = it)
                noteRepository.insert(note)
            }
            inputDialog.dismiss()
        }
        inputDialog.show()
    }

    private fun removeNote(note: Note) {
        noteRepository.delete(note)
        context?.let {
            Snackbar.make(
                    notes_container,
                    it.getString(R.string.noteDeleted, note.title),
                    Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setListeners() {
        add_note_fab.setOnClickListener { addNoteClicked() }
    }

    companion object {
        private const val CARD_ID_KEY = "card_id"
        fun newInstance(cardId: Long) =
                CardNotesFragment().apply {
                    arguments = Bundle().apply {
                        putLong(CARD_ID_KEY, cardId)
                    }
                }
    }
}
