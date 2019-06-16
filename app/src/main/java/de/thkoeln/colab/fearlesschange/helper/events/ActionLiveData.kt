package de.thkoeln.colab.fearlesschange.helper.events

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


class ActionLiveData<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
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