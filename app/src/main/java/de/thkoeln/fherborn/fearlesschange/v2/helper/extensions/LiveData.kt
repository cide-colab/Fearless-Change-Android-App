package de.thkoeln.fherborn.fearlesschange.v2.helper.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

/**
 * Created by florianherborn on 22.08.18.
 */
fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, android.arch.lifecycle.Observer {
        it?.let(observer)
    })
}