package de.thkoeln.colab.fearlesschange.view.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator

class FlipAnimationManager(private val front: View, private val back: View, showBackFirst: Boolean = false) {

    private var frontShown = false

    init {
        when {
            showBackFirst -> flipToBack(false)
            else -> flipToFront(false)
        }
    }

    @Synchronized
    fun flipToFront(animated: Boolean = true) {
        when {
            animated -> animateAndDo(front, back) { switchVisibility(front, back) }
            else -> switchVisibility(front, back)
        }
        frontShown = true
    }

    @Synchronized
    fun flipToBack(animated: Boolean = true) {
        when {
            animated -> animateAndDo(back, front) { switchVisibility(back, front) }
            else -> switchVisibility(back, front)
        }
        frontShown = false
    }

    @Synchronized
    fun flip(animated: Boolean = true) {
        when {
            frontShown -> flipToBack(animated)
            else -> flipToFront(animated)
        }
    }

    private fun switchVisibility(show: View, hide: View) {
        hide.visibility = GONE
        show.visibility = VISIBLE
    }

    private fun animateAndDo(animateIn: View, animateOut: View, andDo: () -> Unit) {
        val oaOut = ObjectAnimator.ofFloat(animateOut, "scaleX", 1f, 0f).apply {
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) { andDo.invoke() }
            })
        }
        val oaIn = ObjectAnimator.ofFloat(animateIn, "scaleX", 0f, 1f).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }

        AnimatorSet().apply {
            playSequentially(oaOut, oaIn)
            duration = 100
            start()
        }
    }
}