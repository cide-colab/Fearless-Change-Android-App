package de.thkoeln.colab.fearlesschange.core.extensions

import androidx.lifecycle.*


fun <T, R> LiveData<T>.switchMap(transform: (T) -> LiveData<R>?) = Transformations.switchMap(this) { transform(it) }
fun <T, R> LiveData<T>.map(transform: (T) -> R) = Transformations.map(this) { transform(it) }
fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = observe(lifecycleOwner, Observer(observer))
fun <T> MediatorLiveData<T>.asLiveData() = this as LiveData<T>
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
fun <T> LiveData<T?>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}