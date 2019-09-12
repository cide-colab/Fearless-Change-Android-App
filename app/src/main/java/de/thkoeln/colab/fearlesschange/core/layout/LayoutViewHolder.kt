package de.thkoeln.colab.fearlesschange.core.layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class LayoutViewHolder<T>(val layout: Int) {
    private var view: View? = null

    fun inflate(parent: ViewGroup, attachToRoot: Boolean = false) = inflate(parent, parent.context, attachToRoot)
    fun inflate(parent: ViewGroup?, context: Context, attachToRoot: Boolean = false) = LayoutInflater.from(context).inflate(layout, parent, attachToRoot)
            .also { view = it }.also { afterViewCreated(it) }.let { this }

    private fun setView(view: View) {
        this.view = view
    }

    fun getView() = view ?: throw Exception("No view setted")

    fun bind(value: T) = view?.let {
        bind(it, value)
    }

    open fun afterViewCreated(view: View) {}
    protected abstract fun bind(view: View, value: T)
}