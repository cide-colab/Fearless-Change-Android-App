package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Bundle
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.helper.ConfirmationRequest
import de.thkoeln.fherborn.fearlesschange.v2.helper.SnackBarMessage
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event
import java.util.*

/**
 * Created by Florian on 24.08.2018.
 */
abstract class BasicViewModel(application: Application): AndroidViewModel(application) {

    private val pendingRequests = mutableMapOf<UUID, Pair<() -> Unit,(() -> Unit)?>>()
    val requestConfirmationEvent = Event<ConfirmationRequest>()
    val sendSnackBarMessageEvent = Event<SnackBarMessage>()


    fun onConfirmRequest(request: ConfirmationRequest) {
        val methods = pendingRequests.getOrElse(request.id){ return }
        pendingRequests.remove(request.id)
        methods.first.invoke()
    }

    fun onCancelRequest(request: ConfirmationRequest) {
        val methods = pendingRequests.getOrElse(request.id){ return }
        pendingRequests.remove(request.id)
        methods.second?.invoke()
    }

    protected fun requestConfirmation(
            textId: Int,
            vararg textArgs: String = emptyArray(),
            titleId: Int? = null,
            positiveButtonTextId: Int = R.string.alert_dialog_positive_button_label,
            negativeButtonTextId: Int = R.string.alert_dialog_negative_button_label,
            onCancel: (() -> Unit) = {},
            onConfirm: () -> Unit
    ) {
        val id = UUID.randomUUID()
        val context = getApplication<Application>()
        val text = context.getString(textId, textArgs)
        val title = titleId?.let { context.getString(it) }
        val positiveButtonText = context.getString(positiveButtonTextId)
        val negativeButtonText = context.getString(negativeButtonTextId)
        pendingRequests[id] = Pair(onConfirm, onCancel)
        requestConfirmationEvent.invoke(ConfirmationRequest(title, text, positiveButtonText, negativeButtonText, id))
    }

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