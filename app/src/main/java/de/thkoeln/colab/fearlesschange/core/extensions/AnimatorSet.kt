package de.thkoeln.colab.fearlesschange.core.extensions

import android.animation.Animator
import android.animation.AnimatorSet


fun AnimatorSet.playSequentially(duration: Long, delay: Long, vararg animators: Animator) = this.apply {
    playSequentially(*animators)
    this.duration = duration
    startDelay = delay
}