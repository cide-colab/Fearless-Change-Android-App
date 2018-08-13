package de.thkoeln.fherborn.fearlesschange.ui.views.cardpopup

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
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

class CardPopup(context: Context?, val card: Card): Dialog(context), CardViewBehaviorProcessor {

    override val cardBehaviors = mutableListOf<CardViewBehavior>()

    private var frontShown = true
    private val notesAdapter = NoteRecyclerGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.layout_card_popup)
        prepareDialog()
        setCard()
        setNotes()
        setListeners()
    }

    private fun setNotes() {
        notes_recycler_view.adapter = notesAdapter
        notes_recycler_view.isNestedScrollingEnabled = false
        updateNotes()
    }

    private fun updateNotes() {
        val notes = listOf(
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(0, "testNote1", "TestNote1Description", 0),
                Note(1, "testNote2", "TestNote2Description", 0),
                Note(2, "testNote3", "TestNote3Description", 0),
                Note(3, "testNote4", "TestNote4Description", 0)
        )
        notesAdapter.notes = notes
        notesAdapter.notifyDataSetChanged()
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
        add_note_fab.setOnClickListener { onAddNoteClicked() }
    }

    private fun onAddNoteClicked() {

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