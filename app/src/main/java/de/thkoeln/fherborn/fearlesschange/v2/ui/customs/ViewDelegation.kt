package de.thkoeln.fherborn.fearlesschange.v2.ui.customs

import android.view.View
import kotlin.reflect.KProperty

/**
 * Created by Florian on 23.08.2018.
 */
open class ViewDelegation<T>(private val default: T, private val setToView: (T) -> Unit) {

    private var value: T = default

    operator fun getValue(thisRef: View?, property: KProperty<*>): T? {
        return value
    }
    operator fun setValue(thisRef: View?, property: KProperty<*>, value: T?) {
        this.value = value?:default
        setToView.invoke(this.value)
        thisRef?.invalidate()
        thisRef?.requestLayout()
    }
}

