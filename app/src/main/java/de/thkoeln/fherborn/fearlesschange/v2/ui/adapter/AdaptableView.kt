package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

import de.thkoeln.fherborn.fearlesschange.v2.ui.customs.PatternCardPreviewView

/**
 * Created by Florian on 23.08.2018.
 */
interface AdaptableView<out V> {
    fun <T> setAdapter(adapter: SingleViewAdapter<T, V>)
}