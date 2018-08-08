package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardpopup.CardPopup
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import kotlinx.android.synthetic.main.fragment_random_cards.*
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.view.View


class RandomCardsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_random_cards, container, false)

    private lateinit var cardRepository: CardRepository

    override fun onStart() {
        super.onStart()

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

    private fun animateCardsAndRun(vararg cards: CardView, run: (CardView) -> Unit) {

        val animators = cards.flatMap {
            val oa1 = ObjectAnimator.ofFloat(it, "scaleX", 1f, 0f)
            val oa2 = ObjectAnimator.ofFloat(it, "scaleX", 0f, 1f)
            val oa3 = ObjectAnimator.ofFloat(it, "scaleX", 1f, 0f)
            val oa4 = ObjectAnimator.ofFloat(it, "scaleX", 0f, 1f)

            oa1.interpolator = DecelerateInterpolator()
            oa2.interpolator = AccelerateDecelerateInterpolator()
            oa3.interpolator = DecelerateInterpolator()
            oa4.interpolator = AccelerateDecelerateInterpolator()

            oa1.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) { run(it) }
            })

            listOf(oa1, oa2, oa3, oa4)
        }

        AnimatorSet().apply {
            playSequentially(animators)
            duration = 100
            start()
        }
    }

    private fun reload(vararg cardViews: CardView) {

        cardRepository.getRandom(cardViews.size).observe(this, Observer { random_cards ->
            var index = 0
            animateCardsAndRun(*cardViews) {
                it.card = random_cards?.get(index++)
            }
        })
    }

    private fun setCardListener(vararg cardViews: CardView) {
        cardViews.forEach {
            it.onCardClickedListener = { view, card ->
                card?.let {
                    CardPopup(view.context, card).show()
                }
            }
        }
    }
}
