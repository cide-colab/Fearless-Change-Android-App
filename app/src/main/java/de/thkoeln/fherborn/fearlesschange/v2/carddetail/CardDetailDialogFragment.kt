package de.thkoeln.fherborn.fearlesschange.v2.carddetail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
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
import de.thkoeln.fherborn.fearlesschange.databinding.CardDetailDialogFragmentBinding
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardActionListener
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewAction
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewBehaviorProcessor
import kotlinx.android.synthetic.main.card_detail_dialog_fragment.*

/**
 * A DialogFragment to show a flippable card detail with its notes
 */
class CardDetailDialogFragment : DialogFragment(), CardViewBehaviorProcessor {


    override val cardBehaviors = mutableListOf<CardActionListener>()

    private lateinit var cardRepository: CardRepository
    private var frontShown = true
    private var card: Card? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val cardId = arguments?.getLong(CARD_ID_KEY)
                ?: throw IllegalArgumentException("Missing CardId in arguments")

        val factory = CardDetailViewModelFactory(context, cardId)
        val cardDetailViewModel = ViewModelProviders.of(this, factory).get(CardDetailViewModel::class.java)

        val binding = DataBindingUtil.inflate<CardDetailDialogFragmentBinding>(
                inflater,
                R.layout.card_detail_dialog_fragment,
                container,
                false
        ).apply {
            viewModel = cardDetailViewModel
            setLifecycleOwner(this@CardDetailDialogFragment)
            val flipBehavior = object : CardActionListener {
                override fun onCardAction(card: Card?, action: CardViewAction) {
                    if (action == CardViewAction.CARD_CLICKED)
                        flip()
                }
            }
            popup_card_front.addDistinctCardActionListener(flipBehavior)
            popup_card_back.addDistinctCardActionListener(flipBehavior)

            //TODO via event ins viewmodel
            //popup_card_front.addCardActionListener(cardActionListener)
            //popup_card_back.addCardActionListener(cardActionListener)
        }

        return binding.root
    }

//TODO
//    /**
//     * Inflates the notes fragment to show the notes of the given card
//     * @param card Card to show the notes of
//     */
//    private fun inflateNotesFragment(card: Card?) {
//        card?.let {
//            //Bugfix return if fragment isn't attached
//            if (!isAdded) return@let
//            val notesFragment = CardNotesFragment.newInstance(cardId = it.id)
//            childFragmentManager.beginTransaction()
//                    .replace(R.id.bottom_sheet_content, notesFragment).commit()
//        }
//    }


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
                CardDetailDialogFragment().apply {
                    arguments = Bundle().apply {
                        putLong(CARD_ID_KEY, cardId)
                    }
                }
    }
}