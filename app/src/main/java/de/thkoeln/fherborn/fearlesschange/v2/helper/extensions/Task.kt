package de.thkoeln.fherborn.fearlesschange.v2.helper.extensions

import android.os.AsyncTask
import de.thkoeln.fherborn.fearlesschange.persistance.DatabaseTask

/**
 * Created by florianherborn on 22.08.18.
 */
class DatabaseTask : AsyncTask<() -> Unit, Unit, Unit>() {
    override fun doInBackground(vararg f: () -> Unit) {
        f.forEach { it.invoke() }
    }
}

fun doAsync(f: () -> Unit) {
    DatabaseTask().execute(f)
}