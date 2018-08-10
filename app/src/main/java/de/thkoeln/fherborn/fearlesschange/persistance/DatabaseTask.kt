package de.thkoeln.fherborn.fearlesschange.persistance

import android.os.AsyncTask

/**
 * Created by Florian on 10.08.2018.
 */
class DatabaseTask : AsyncTask<() -> Unit, Void, Void>() {
    override fun doInBackground(vararg f: () -> Unit): Void? {
        f.forEach { it.invoke() }
        return null
    }
}

fun runInBackground(f: () -> Unit) = DatabaseTask().execute(f)