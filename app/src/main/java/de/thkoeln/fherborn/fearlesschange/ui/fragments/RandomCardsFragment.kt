package de.thkoeln.fherborn.fearlesschange.ui.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardpopup.CardPopup
import de.thkoeln.fherborn.fearlesschange.ui.views.cardview.CardView
import kotlinx.android.synthetic.main.fragment_random_cards.*
import android.view.animation.AnimationSet



class RandomCardsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_random_cards, container, false)

    private lateinit var cardRepository: CardRepository

    override fun onStart() {
        super.onStart()

        cardRepository = CardRepository(activity?.application)

        reload()
        setListeners()

    }

    private fun setListeners() {
        setCardListener(random_cards_1, random_cards_2, random_cards_3)
        random_cards_reload.setOnClickListener { reload() }
    }

    fun reload() {
        val rotateOut = AnimationUtils.loadAnimation(context, R.anim.rotate_out)
        val rotateIn = AnimationUtils.loadAnimation(context, R.anim.rotate_in)

        rotateOut.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                loadRandomCardsToView(random_cards_1, random_cards_2, random_cards_3)
                random_cards_container.startAnimation(rotateIn)
            }
            override fun onAnimationStart(animation: Animation?) {}
        })

        random_cards_container.startAnimation(rotateOut)
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

    private fun loadRandomCardsToView(vararg cardViews: CardView) {
        cardRepository.getRandom(cardViews.size).observe(this, Observer {
            it?.forEachIndexed{index, card -> cardViews[index].card = card}
        })
    }
}
