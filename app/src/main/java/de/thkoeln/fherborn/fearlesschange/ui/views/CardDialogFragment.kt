package de.thkoeln.fherborn.fearlesschange.ui.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.fragments.CardNotesFragment
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewBehavior
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewBehaviorProcessor
import kotlinx.android.synthetic.main.layout_card_popup_fragment.*

/**
 * A DialogFragment to show a flippable card detail with its notes
 */
class CardDialogFragment : DialogFragment(), CardViewBehaviorProcessor {


    override val cardBehaviors = mutableListOf<CardViewBehavior>()

    private lateinit var cardRepository: CardRepository
    private var frontShown = true
    private var card: Card? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppDialog)
        val viewTarget = ViewTarget(R.layout.fragment_card_notes, activity)
        ShowcaseView.Builder(activity)
                .setTarget(viewTarget)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("Here you can put your personal Notes!")
                .build()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.layout_card_popup_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardRepository = CardRepository(context)
        arguments?.getLong(CARD_ID_KEY)?.let {
            cardRepository.getById(it).observe(activity as LifecycleOwner, Observer {
                setCard(it)
            })
        }
        setListeners()
    }

    /**
     * Sets the content of the card to the cardviews and inflates the notes fragment
     */
    private fun setCard(card: Card?) {
        this.card = card
        popup_card_front?.card = card
        popup_card_back?.card = card
        inflateNotesFragment(card)
    }

    /**
     * Inflates the notes fragment to show the notes of the given card
     * @param card Card to show the notes of
     */
    private fun inflateNotesFragment(card: Card?) {
        card?.let {
            //Bugfix return if fragment isn't attached
            if (!isAdded) return@let
            val notesFragment = CardNotesFragment.newInstance(cardId = it.id)
            childFragmentManager.beginTransaction()
                    .replace(R.id.bottom_sheet_content, notesFragment).commit()
        }
    }

    /**
     * Sets given card behaviors and flip behavior
     */
    private fun setListeners() {
        val flipBehavior = object : CardViewBehavior {
            override fun onCardAction(cardView: CardView, card: Card?, action: CardViewAction) {
                if (action == CardViewAction.CARD_CLICKED)
                    flip()
            }
        }
        popup_card_back.addBehaviors(cardBehaviors)
        popup_card_front.addBehaviors(cardBehaviors)
        popup_card_back.addDistinctBehaviors(flipBehavior)
        popup_card_front.addDistinctBehaviors(flipBehavior)
    }

    /**
     * Flips the card
     */
    @Synchronized
    private fun flip() {
        frontShown = if (frontShown) {
            switch(popup_card_front, popup_card_back)
            false
        } else {
            switch(popup_card_back, popup_card_front)
            true
        }
    }

    /**
     * Switches and animates from [from] to [to]
     * [from] will be hided and [to] will be visible
     * @param from view to be hided
     * @param to view to be shown
     */
    @Synchronized
    private fun switch(from: View, to: View) {
        val oaOut = ObjectAnimator.ofFloat(from, "scaleX", 1f, 0f).apply {
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    from.visibility = GONE
                    to.visibility = VISIBLE
                }
            })
        }
        val oaIn = ObjectAnimator.ofFloat(to, "scaleX", 0f, 1f).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }

        AnimatorSet().apply {
            playSequentially(oaOut, oaIn)
            duration = 100
            start()
        }
    }

    companion object {
        private const val CARD_ID_KEY = "card_id"
        fun newInstance(cardId: Long) =
                CardDialogFragment().apply {
                    arguments = Bundle().apply {
                        putLong(CARD_ID_KEY, cardId)
                    }
                }
    }
}