package de.thkoeln.colab.fearlesschange.core.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackbar(text: Int, duration: Int = 1) = Snackbar.make(this, text, duration)
fun View.makeSnackbar(text: String = "", duration: Int = 1000) = Snackbar.make(this, text, duration)