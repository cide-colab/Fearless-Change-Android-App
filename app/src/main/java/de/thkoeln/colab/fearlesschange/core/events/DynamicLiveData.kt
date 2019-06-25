package de.thkoeln.colab.fearlesschange.core.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class DynamicLiveData<T> : MediatorLiveData<T>() {
    private var oldSource: LiveData<T>? = null
    private var then: (T) -> Unit = {}

    fun newSource(liveData: LiveData<T>) = this.apply {
        oldSource?.let { removeSource(it) }
        oldSource = liveData
        addSource(liveData) {
            value = it
            then(it)
        }
    }

    fun then(block: (T) -> Unit): DynamicLiveData<T> = this.apply { this.then = block }
}