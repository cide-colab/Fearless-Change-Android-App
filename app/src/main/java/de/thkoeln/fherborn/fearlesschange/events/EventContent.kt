package de.thkoeln.fherborn.fearlesschange.events

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer


open class EventContent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}


class ObservableEvent<T> {
    private val liveData = MutableLiveData<EventContent<T>>()

    fun observeOnce(owner: LifecycleOwner, observer: (T?) -> Unit) {
        liveData.observe(owner, Observer{ event ->
          event?.getContentIfNotHandled()?.let { observer.invoke(it) }
        })
    }

    fun observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
        liveData.observe(owner, Observer{ event ->
            observer.invoke(event?.peekContent())
        })
    }


    fun invoke(data: T) {
        liveData.value = EventContent(data)
    }
}