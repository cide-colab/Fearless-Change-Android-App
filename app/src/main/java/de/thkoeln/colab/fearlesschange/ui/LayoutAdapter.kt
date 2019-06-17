package de.thkoeln.colab.fearlesschange.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class LayoutAdapter<T>(val layout: Int) {
    private var view: View? = null
    fun inflate(parent: ViewGroup, attachToRoot: Boolean = false) = inflate(parent, parent.context, attachToRoot)
    fun inflate(parent: ViewGroup?, context: Context, attachToRoot: Boolean = false) = LayoutInflater.from(context).inflate(layout, parent, attachToRoot).also { view = it }
    fun setView(view: View) {
        this.view = view
    }

    fun bind(value: T) = view?.let { bind(it, value) }
    protected abstract fun bind(view: View, value: T)
}