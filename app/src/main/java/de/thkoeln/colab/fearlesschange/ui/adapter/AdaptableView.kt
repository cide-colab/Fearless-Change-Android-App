package de.thkoeln.colab.fearlesschange.ui.adapter

/**
 * Created by Florian on 23.08.2018.
 */
interface AdaptableView<out V> {
    fun <T> setAdapter(adapter: SingleViewAdapter<T, V>)
}