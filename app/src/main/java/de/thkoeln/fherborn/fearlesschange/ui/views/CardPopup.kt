package de.thkoeln.fherborn.fearlesschange.ui.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.NoteRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.*
import kotlinx.android.synthetic.main.layout_card_popup.*
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository


//TODO Logik für notes auslagern?
class CardPopup(private val activity: AppCompatActivity?, val card: Card): Dialog(activity), CardViewBehaviorProcessor {


    override val cardBehaviors = mutableListOf<CardViewBehavior>()

    private var frontShown = true
    private val notesAdapter = NoteRecyclerGridAdapter()
    private val noteRepository = NoteRepository(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.layout_card_popup)
        prepareDialog()
        setCard()
        setUpNotes()
        setListeners()
    }
    private fun setUpNotes() {
        notes_recycler_view.adapter = notesAdapter
        notes_recycler_view.isNestedScrollingEnabled = false
        notes_recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        activity?.let {
            noteRepository.getByCardId(card.id).observe(it, Observer {
                notesAdapter.notes = it?: listOf()
                notesAdapter.notifyDataSetChanged()
            })
        }
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
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
            val note = Note(title = title, description = description, cardId = card.id)
            noteRepository.insert(note)
            inputDialog.dismiss()
        }
        inputDialog.show()
    }

    private fun removeNote(note: Note) {
        noteRepository.delete(note)
        Snackbar.make(card_popup_container, context.getString(R.string.noteDeleted, note.title), Snackbar.LENGTH_SHORT).show()
    }

    private fun setCard() {
        popup_card_front.card = card
        popup_card_back.card = card
    }

    private fun setListeners() {
        val flipBehavior = object: CardViewBehavior {
            override fun onCardAction(cardView: CardView, card: Card?, action: CardViewAction) {
                if (action == CardViewAction.CARD_CLICKED)
                    flipCard()
            }
        }
        popup_card_back.addBehaviors(cardBehaviors)
        popup_card_front.addBehaviors(cardBehaviors)
        popup_card_back.addDistinctBehaviors(flipBehavior)
        popup_card_front.addDistinctBehaviors(flipBehavior)
        add_note_fab.setOnClickListener { addNoteClicked() }
    }

    @Synchronized private fun flipCard() {
        frontShown = if (frontShown) {
            switchViews(popup_card_back, popup_card_front)
            false
        } else {
            switchViews(popup_card_front, popup_card_back)
            true
        }
    }

    @Synchronized private fun switchViews(showView: View, hideView: View) {
        val oaOut = ObjectAnimator.ofFloat(hideView, "scaleX", 1f, 0f).apply {
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    hideView.visibility = GONE
                    showView.visibility = VISIBLE
                }
            })
        }
        val oaIn = ObjectAnimator.ofFloat(showView, "scaleX", 0f, 1f).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }

        AnimatorSet().apply {
            playSequentially(oaOut, oaIn)
            duration = 100
            start()
        }
    }

    private fun prepareDialog() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    /*
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        dismiss()
        return true
    }
    */
}