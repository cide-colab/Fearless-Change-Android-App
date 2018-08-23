package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardViewPreview
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternRepository
import kotlinx.android.synthetic.main.fragment_random_cards.*


class RandomCardsFragment : Fragment() {

    private lateinit var cardRepository: PatternRepository
    private var generated = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_random_cards, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardRepository = PatternRepository(activity!!.application)

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
        cardRepository.get(1).observe(this, Observer { random_cards ->
            random_cards?.let {
                /*
                if (random_cards.size < cardViews.size || generated) return@Observer
                generated = true
                animateCardsAndRun(*cardViews) { cardView, index ->
                    cardView.card = random_cards[index].card
                    cardView.notesCount = random_cards[index].noteCount
                }
                */
            }
        })
    }

    private fun setCardListener(vararg cardViews: CardViewPreview) {
        cardViews.forEach {
        }
    }
}
