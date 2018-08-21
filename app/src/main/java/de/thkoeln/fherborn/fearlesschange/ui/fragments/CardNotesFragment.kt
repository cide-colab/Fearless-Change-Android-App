package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
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
import de.thkoeln.fherborn.fearlesschange.databinding.LayoutCreateNoteDialogBinding
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository
import de.thkoeln.fherborn.fearlesschange.toBackgroundOf
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideApp
import de.thkoeln.fherborn.fearlesschange.ui.handler.CreateNoteHandler
import de.thkoeln.fherborn.fearlesschange.ui.viewmodels.CardNoteViewModel
import kotlinx.android.synthetic.main.fragment_card_notes.*


class CardNotesFragment : Fragment() {

    private lateinit var noteRepository: NoteRepository
    private val notesAdapter = NoteRecyclerGridAdapter()
    private var cardId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_card_notes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardId = arguments?.getLong(CARD_ID_KEY) ?: throw IllegalArgumentException("To show notes you need to pass a cardId as argument")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        noteRepository = NoteRepository(context)
        setupView()
        setupNotes()
        setListeners()
    }

    private fun setupView() {

        GlideApp.with(this)
                .load(R.drawable.notes_bg).fitCenter()
                .toBackgroundOf(notes_container)

        GlideApp.with(this)
                .load(R.drawable.wood).fitCenter()
                .toBackgroundOf(notes_label)
    }

    private fun setupNotes() {
        notes_recycler_view.adapter = notesAdapter
        notes_recycler_view.isNestedScrollingEnabled = false
        notes_recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        noteRepository.getByCardId(cardId).observe(activity as AppCompatActivity, Observer {
            notesAdapter.notes = it ?: listOf()
            notesAdapter.notifyDataSetChanged()
        })
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
        val noteViewModel = ViewModelProviders.of(this).get(CardNoteViewModel::class.java)

        val binding = DataBindingUtil.inflate<LayoutCreateNoteDialogBinding>(
                LayoutInflater.from(context),
                R.layout.layout_create_note_dialog,
                null,
                false
        ).apply {
            note = noteViewModel.getRawNote(cardId)
            handler = CreateNoteHandler(noteViewModel)
        }

        val dialog = Dialog(context).apply {
            setContentView(binding.root)
            show()
        }

        noteViewModel.noteCreatedEvent.observe(this, Observer { it?.let {
            if (it) {
                dialog.dismiss()
            }
        } })


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
