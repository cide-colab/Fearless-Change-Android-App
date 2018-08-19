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
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.fragments.CardNotesFragment
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewBehavior
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewBehaviorProcessor
import kotlinx.android.synthetic.main.layout_card_popup_fragment.*


class CardPopupFragment : DialogFragment(), CardViewBehaviorProcessor {


    override val cardBehaviors = mutableListOf<CardViewBehavior>()

    private lateinit var cardRepository: CardRepository
    private var frontShown = true
    private var card: Card? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppDialog)
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

    private fun setCard(card: Card?) {
        this.card = card
        popup_card_front?.card = card
        popup_card_back?.card = card
        card?.let {
            val notesFragment = CardNotesFragment.newInstance(cardId = it.id)
            childFragmentManager.beginTransaction()
                    .replace(R.id.bottom_sheet_content, notesFragment).commit()
        }
    }

    private fun setListeners() {
        val flipBehavior = object : CardViewBehavior {
            override fun onCardAction(cardView: CardView, card: Card?, action: CardViewAction) {
                if (action == CardViewAction.CARD_CLICKED)
                    flipCard()
            }
        }
        popup_card_back.addBehaviors(cardBehaviors)
        popup_card_front.addBehaviors(cardBehaviors)
        popup_card_back.addDistinctBehaviors(flipBehavior)
        popup_card_front.addDistinctBehaviors(flipBehavior)
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

    companion object {
        private const val CARD_ID_KEY = "card_id"
        fun newInstance(cardId: Long) =
                CardPopupFragment().apply {
                    arguments = Bundle().apply {
                        putLong(CARD_ID_KEY, cardId)
                    }
                }
    }
}