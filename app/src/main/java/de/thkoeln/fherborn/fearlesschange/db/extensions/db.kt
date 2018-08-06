package de.thkoeln.fherborn.fearlesschange.db.extensions

import android.app.Activity
import android.support.v4.app.Fragment
import de.thkoeln.fherborn.fearlesschange.App
import io.reactivex.Flowable
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by florianherborn on 06.08.18.
 */

fun <T> Fragment.loadInBackground(runInBackground: (App) -> Flowable<T>, runOnUiThread: ((T) -> Unit)?) {
    activity?.loadInBackground(runInBackground, runOnUiThread)
}

fun <T> Activity.loadInBackground(runInBackground: (App) -> Flowable<T>, runOnUiThread: ((T) -> Unit)?) {
    doAsync {
        runInBackground.invoke(application as App).subscribe { result ->
            uiThread { runOnUiThread?.invoke(result) }
        }
    }
}