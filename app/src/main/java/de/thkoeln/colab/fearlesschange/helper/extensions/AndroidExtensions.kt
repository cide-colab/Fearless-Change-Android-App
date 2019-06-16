package de.thkoeln.colab.fearlesschange.helper.extensions

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/**
 * Created by florianherborn on 08.08.18.
 */
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
fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

class DatabaseTask : AsyncTask<() -> Unit, Unit, Unit>() {
    override fun doInBackground(vararg f: () -> Unit) {
        f.forEach { it.invoke() }
    }
}

fun doAsync(f: () -> Unit) {
    DatabaseTask().execute(f)
}

