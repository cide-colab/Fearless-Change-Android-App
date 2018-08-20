package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import kotlinx.android.synthetic.main.fragment_random_cards.*
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.view.View
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewPreview
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.behaviors.DefaultCardPreviewBehavior
import kotlinx.android.synthetic.main.layout_card_view_preview.view.*


class RandomCardsFragment : Fragment() {

    private lateinit var cardRepository: CardRepository
    private var generated = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_random_cards, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardRepository = CardRepository(activity?.application)

        reloadAll()
        setListeners()
    }


    private fun reloadAll() {
        reload(random_cards_1, random_cards_2, random_cards_3)
    }

    private fun setListeners() {
        setCardListener(random_cards_1, random_cards_2, random_cards_3)
        random_cards_reload.setOnClickListener { reloadAll() }
    }

    private fun animateCardsAndRun(vararg cards: CardViewPreview, delayBetweenAnimations: Long = 100, durationPerAnimation: Long = 100, run: (CardViewPreview, Int) -> Unit) {

        val animators = cards.mapIndexed { index, cardView ->
            val oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f).apply {
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        run(cardView, index)
                    }
                })
            }
            val oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }
            val oa3 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
            val oa4 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }

            AnimatorSet().apply {
                playSequentially(oa1, oa2, oa3, oa4)
                duration = durationPerAnimation
                startDelay = delayBetweenAnimations * index
            }
        }
        animators.forEach { it.start() }
    }

    private fun reload(vararg cardViews: CardViewPreview) {
        generated = false
        cardRepository.getRandomWithNotesCount(cardViews.size).observe(this, Observer { random_cards ->
            random_cards?.let {
                if (random_cards.size < cardViews.size || generated) return@Observer
                generated = true
                animateCardsAndRun(*cardViews) { cardView, index ->
                    cardView.card = random_cards[index].card
                    cardView.notesCount = random_cards[index].noteCount
                }
            }
        })
    }

    private fun setCardListener(vararg cardViews: CardViewPreview) {
        cardViews.forEach {
            it.addBehaviors(DefaultCardPreviewBehavior(activity as AppCompatActivity))
        }
    }
}
