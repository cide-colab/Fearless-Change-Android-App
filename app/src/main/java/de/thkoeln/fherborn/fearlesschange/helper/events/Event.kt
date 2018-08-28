package de.thkoeln.fherborn.fearlesschange.helper.events

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread


class Event<T> : MutableLiveData<T>() {

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T?>) {

        if (hasObservers()) {
            throw Throwable("Only one observer at a time may subscribe to a ActionLiveData")
        }

        super.observe(owner, Observer { data ->
            data?.let {
                observer.onChanged(it)
                value = null
            }
        })
    }

    @MainThread
    fun invoke(data: T) {
        value = data
    }
}