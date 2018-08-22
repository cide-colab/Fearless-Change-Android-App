package de.thkoeln.fherborn.fearlesschange.v2.carddetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.NoteRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.v2.animation.FlipAnimationHelper
import de.thkoeln.fherborn.fearlesschange.v2.extensions.setOptimizedBackground
import de.thkoeln.fherborn.fearlesschange.v2.extensions.setOptimizedImage
import de.thkoeln.fherborn.fearlesschange.v2.viewmodel.CardViewModel
import kotlinx.android.synthetic.main.card_back.*
import kotlinx.android.synthetic.main.card_detail_dialog.*
import kotlinx.android.synthetic.main.card_front.*


/**
 * A DialogFragment to show a flippable card detail with its notes
 */
class CardDetailDialogFragment : DialogFragment() {

    private lateinit var viewModel: CardViewModel
    private val noteAdapter = NoteRecyclerGridAdapter()
    private var cardId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardId = arguments?.getLong(CARD_ID_KEY)
        viewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.card_detail_dialog, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgrounds()
        initCards()
        initNotes()
    }

    private fun initNotes() {
        card_detail_dialog_add_note_btn.setOnClickListener { openCreateNoteDialog() }
        card_detail_dialog_notes_recycler_view.adapter = noteAdapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                viewHolder?.adapterPosition?.let {
                    deleteNote(noteAdapter.notes[it])
                }
            }
        }).attachToRecyclerView(card_detail_dialog_notes_recycler_view)
        viewModel.getNotes(cardId).observe(this, Observer { onNotesUpdate(it) })
    }

    private fun deleteNote(note: Note) {
        viewModel.deleteNote(note)
        Snackbar.make(card_detail_dialog_bottom_sheet, getString(R.string.noteDeleted, note.title), 2000).show()
    }

    private fun initCards() {
        val flipAnimationHelper = FlipAnimationHelper(card_front, card_back)
        card_front_card.setOnClickListener { flipAnimationHelper.flipToBack() }
        card_front_favorite_btn.setOnClickListener { viewModel.switchFavorite(cardId) }

        card_back_card.setOnClickListener { flipAnimationHelper.flipToFront() }
        card_back_favorite_btn.setOnClickListener { viewModel.switchFavorite(cardId) }

        viewModel.getCard(cardId).observe(this, Observer { onCardUpdate(it) })
    }

    private fun onNotesUpdate(it: List<Note>?) {
        noteAdapter.notes = it ?: listOf()
        noteAdapter.notifyDataSetChanged()
    }

    private fun onCardUpdate(card: Card?) {
        val favoriteDrawable = when (card?.favorite) {
            true -> R.drawable.ic_favorite_full_white
            else -> R.drawable.ic_favorite_white
        }

        card_front_title.text = card?.title
        card_front_summary.text = card?.summary
        card_front_image.setOptimizedImage(card?.pictureName, R.drawable.default_pattern_image)
        card_front_favorite_btn.setImageResource(favoriteDrawable)

        card_back_title.text = card?.title
        card_back_problem.text = card?.problem
        card_back_solution.text = card?.solution
        card_back_favorite_btn.setImageResource(favoriteDrawable)
    }

    private fun openCreateNoteDialog() {
        val dialog = CreateNoteDialog(context)
        dialog.onConfirmListener = { title, description ->
            viewModel.createNote(cardId, title, description)
        }
        dialog.show()
    }

    private fun setBackgrounds() {
        card_front_wrapper.setOptimizedBackground(R.drawable.card_bg)
        card_front_content.setOptimizedBackground(R.drawable.card_content_bg)
        card_back_wrapper.setOptimizedBackground(R.drawable.card_bg)
        card_back_content.setOptimizedBackground(R.drawable.card_content_bg)
        card_detail_dialog_bottom_sheet.setOptimizedBackground(R.drawable.notes_bg)
        card_detail_dialog_notes_header.setOptimizedBackground(R.drawable.notes_header_bg)
    }

    companion object {
        private const val CARD_ID_KEY = "card_id"
        fun newInstance(cardId: Long) =
                CardDetailDialogFragment().apply {
                    arguments = Bundle().apply {
                        putLong(CARD_ID_KEY, cardId)
                    }
                }
    }
}