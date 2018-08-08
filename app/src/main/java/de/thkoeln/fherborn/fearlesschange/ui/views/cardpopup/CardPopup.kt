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
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import kotlinx.android.synthetic.main.layout_card_popup.*

class CardPopup(context: Context, val card: Card): Dialog(context) {

    private var frontShown = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.layout_card_popup)
        setParams()
        popup_card_front.card = card
        popup_card_back.card = card
        setListeners()
    }

    private fun setListeners() {
        popup_details_btn.setOnClickListener {
            flipCard()
//            startCardDetailActivity()
        }
    }

    @Synchronized private fun flipCard() {
        if (frontShown) {
            switchViews(popup_card_back, popup_card_front)
            frontShown = false
        } else {
            switchViews(popup_card_front, popup_card_back)
            frontShown = true
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

    private fun setParams() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        dismiss()
        return true
    }
}