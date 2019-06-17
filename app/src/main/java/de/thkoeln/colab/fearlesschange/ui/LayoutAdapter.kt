package de.thkoeln.colab.fearlesschange.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.colab.fearlesschange.R

abstract class LayoutAdapter<T>(val layout: Int) {
    private var view: View? = null
    fun inflate(parent: ViewGroup) = inflate(parent, parent.context)
    fun inflate(parent: ViewGroup?, context: Context) = LayoutInflater.from(context).inflate(R.layout.pattern_preview_fragment, parent, false).also { view = it }
    fun setView(view: View) {
        this.view = view
    }

    fun bind(value: T) = view?.let { bind(it, value) }
    protected abstract fun bind(view: View, value: T)
}