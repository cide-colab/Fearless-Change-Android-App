package de.thkoeln.colab.fearlesschange.core.events

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


class SingleActionLiveData<T> : MutableLiveData<T>() {
    private var observer: Observer<in T>? = null

    fun switchObserve(owner: LifecycleOwner, observer: (T) -> Unit) = observe(owner, Observer(observer))
    fun switchObserve(owner: LifecycleOwner, observer: Observer<in T>) {
        this.observer = this.observer?.let {
            removeObserver(it)
            observer
        } ?: observer
        observe(owner, observer)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        this.observer?.let { throw Throwable("Only one observer at a time may subscribe to a SingleActionLiveData") }
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