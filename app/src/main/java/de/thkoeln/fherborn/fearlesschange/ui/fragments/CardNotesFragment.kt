package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.NoteRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.databinding.FragmentCardNotesBinding
import de.thkoeln.fherborn.fearlesschange.databinding.LayoutCreateNoteDialogBinding
import de.thkoeln.fherborn.fearlesschange.ui.handler.CreateNoteHandler
import de.thkoeln.fherborn.fearlesschange.ui.viewmodels.CardNoteViewModel
import kotlinx.android.synthetic.main.fragment_card_notes.*
import kotlinx.android.synthetic.main.layout_card_view_front.*


class CardNotesFragment : Fragment() {

    private lateinit var viewModel: CardNoteViewModel
    private val createNoteDialog by lazy {
        val binding = DataBindingUtil.inflate<LayoutCreateNoteDialogBinding>(
                LayoutInflater.from(context),
                R.layout.layout_create_note_dialog,
                null,
                false
        ).apply {
            note = viewModel.getRawNote()
            handler = CreateNoteHandler(viewModel)
        }

        Dialog(context).apply {
            setContentView(binding.root)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CardNoteViewModel::class.java)
        viewModel.cardId = arguments?.getLong(CARD_ID_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            DataBindingUtil.inflate<FragmentCardNotesBinding>(
                    LayoutInflater.from(context),
                    R.layout.fragment_card_notes,
                    container,
                    false
            ).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNotes()
        setListeners()
    }

    private fun setupNotes() {
        val adapter = NoteRecyclerGridAdapter()
        notes_recycler_view.adapter = adapter
        notes_recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.cardNotes.observe(this, Observer {
            adapter.notes = it ?: listOf()
            adapter.notifyDataSetChanged()
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                when (swipeDir) {
                    ItemTouchHelper.LEFT -> viewModel.removeNote(adapter.notes[viewHolder.adapterPosition])
                }
            }
        }).attachToRecyclerView(notes_recycler_view)

        viewModel.noteRemovedEvent.observe(this, Observer {
            Snackbar.make(
                    notes_container,
                    getString(R.string.noteDeleted, it?.title),
                    Snackbar.LENGTH_SHORT
            ).show()
        })
    }

    private fun setListeners() {
        add_note_fab.setOnClickListener {
            createNoteDialog.show()
            viewModel.noteCreatedEvent.observe(this, Observer { _ ->
                Log.e("SS","SFAF")
                createNoteDialog.dismiss()
            })
        }
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
