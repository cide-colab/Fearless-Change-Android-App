package de.thkoeln.fherborn.fearlesschange.persistance

import android.os.AsyncTask

/**
 * Created by Florian on 10.08.2018.
 */
class DatabaseTask : AsyncTask<() -> Unit, Unit, Unit>() {
    override fun doInBackground(vararg f: () -> Unit) {
        f.forEach { it.invoke() }
    }
}

fun doAsync(f: () -> Unit) {
    DatabaseTask().execute(f)
}