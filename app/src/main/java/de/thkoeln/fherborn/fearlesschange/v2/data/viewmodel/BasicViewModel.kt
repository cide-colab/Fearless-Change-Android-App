package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Bundle
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.helper.SnackBarMessage
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event

/**
 * Created by Florian on 24.08.2018.
 */
abstract class BasicViewModel(application: Application): AndroidViewModel(application) {
    val sendSnackBarMessageEvent = Event<SnackBarMessage>()

    protected fun sendMessage(messageId: Int, vararg args: Any = emptyArray()) {
        val message = when {
            args.isNotEmpty() -> getApplication<Application>().getString(messageId, args)
            else -> getApplication<Application>().getString(messageId)
        }
        sendSnackBarMessageEvent.invoke(SnackBarMessage(message))
    }

    protected fun forceGetNonNullId(id: Long?) = id
            ?: throw IllegalArgumentException("Missing pattern id")


    fun extractRequiredId(bundle: Bundle?, key: String): Long {
        val id = bundle?.getLong(key)
        return when (id) {
            null -> {
                sendMessage(R.string.could_not_find_pattern)
                throw RuntimeException("Missing argument $key")
            }
            else -> id
        }
    }
}