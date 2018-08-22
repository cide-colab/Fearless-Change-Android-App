package de.thkoeln.fherborn.fearlesschange.v2.ui.adapter

import android.view.View

/**
 * Created by florianherborn on 22.08.18.
 */
abstract class SingleViewAdapter<T>(protected val container: View) {
    abstract fun setData(data: T?)
}