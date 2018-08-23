package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

/**
 * Created by florianherborn on 22.08.18.
 */
abstract class SingleViewAdapter<in T, in V> {
    private var currentData: T? = null
    private var registeredView: V? = null
    fun registerView(view: V) {
        registeredView = view
        change(currentData)
    }
    fun change(data: T?) {
        currentData = data
        registeredView?.let { onDataChange(it, data) }
    }
    protected abstract fun onDataChange(view: V, data: T?)
}