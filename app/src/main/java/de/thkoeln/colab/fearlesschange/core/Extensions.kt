package de.thkoeln.colab.fearlesschange.core

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.AsyncTask
import androidx.lifecycle.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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

fun Context.getResourceId(resName: String?, resIdentifier: String) =
        resName?.let {
            try {
                val resId = resources.getIdentifier(resName, resIdentifier, packageName)
                if (resId <= 0) null else resId
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

class DatabaseTask : AsyncTask<() -> Unit, Unit, Unit>() {
    override fun doInBackground(vararg f: () -> Unit) {
        f.forEach { it.invoke() }
    }
}

fun doAsync(f: () -> Unit) {
    DatabaseTask().execute(f)
}



fun ObjectAnimator.onAnimationEnd(listener: (animator: Animator) -> Unit) {
    addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            listener(animation)
        }
    })

}

fun AnimatorSet.playSequentially(duration: Long, delay: Long, vararg animators: Animator) = this.apply {
    playSequentially(*animators)
    this.duration = duration
    startDelay = delay
}


fun <R, T> SharedPreferences.bind(owner: R, key: String, default: T): ReadWriteProperty<R, T> {
    return object : ReadWriteProperty<R, T> {


        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: R, property: KProperty<*>) = when (default) {
            is String -> getString(key, default) as T
            is Long -> getLong(key, default) as T
            is Int -> getInt(key, default) as T
            is Boolean -> getBoolean(key, default) as T
            is Float -> getFloat(key, default) as T
            else -> throw Exception("Incompatible type for shared preferences bind")
        }

        override fun setValue(thisRef: R, property: KProperty<*>, value: T): Unit = when (value) {
            is String -> edit().putString(key, value).apply()
            is Long -> edit().putLong(key, value).apply()
            is Int -> edit().putInt(key, value).apply()
            is Boolean -> edit().putBoolean(key, value).apply()
            is Float -> edit().putFloat(key, value).apply()
            else -> throw Exception("Incompatible type for shared preferences bind")
        }

    }
}


fun ViewPager.onPageScrolled(listener: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            listener(position, positionOffset, positionOffsetPixels)
        }
    })
}

fun Snackbar.onTimeout(listener: () -> Unit) = this.apply {
    addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                listener()
            }
        }
    })
}

fun Context.getDrawable(name: String): Int? =
        getResourceId(name, "drawable")


fun Int.toDp() = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()