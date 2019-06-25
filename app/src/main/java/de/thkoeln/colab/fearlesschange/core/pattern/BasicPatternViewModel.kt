package de.thkoeln.colab.fearlesschange.core.pattern

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.data.ConfirmationRequest
import de.thkoeln.colab.fearlesschange.core.data.SnackBarMessage
import de.thkoeln.colab.fearlesschange.core.events.SingleActionLiveData
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.persistance.statistic.Statistic
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticAction
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticRepository
import java.util.*

abstract class BasicPatternViewModel(application: Application) : AndroidViewModel(application) {

    protected val patternRepository by lazy { PatternRepository(application) }
    protected val statisticRepository by lazy { StatisticRepository(application) }

    private val pendingRequests = mutableMapOf<UUID, Pair<() -> Unit, (() -> Unit)?>>()
    val requestConfirmationEvent = SingleActionLiveData<ConfirmationRequest>()


    fun onConfirmRequest(request: ConfirmationRequest) {
        val methods = pendingRequests.getOrElse(request.id) { return }
        pendingRequests.remove(request.id)
        methods.first.invoke()
    }

    fun onCancelRequest(request: ConfirmationRequest) {
        val methods = pendingRequests.getOrElse(request.id) { return }
        pendingRequests.remove(request.id)
        methods.second?.invoke()
    }

    protected fun requestConfirmation(
            textId: Int,
            vararg textArgs: String = emptyArray(),
            titleId: Int? = null,
            positiveButtonTextId: Int = R.string.action_confirm,
            negativeButtonTextId: Int = R.string.action_cancel,
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

    val actionEvent = SingleActionLiveData<NavDirections>()
    val snackbarEvent = SingleActionLiveData<SnackBarMessage>()

    protected fun notifyPatternClicked(patternInfo: PatternInfo) {
        statisticRepository.insert(Statistic(patternId = patternInfo.pattern.id, action = StatisticAction.CLICK))
    }

    protected fun notifyAction(navDirections: NavDirections) {
        actionEvent.invoke(navDirections)
    }

    protected fun notify(messageId: Int, vararg args: Any = emptyArray()) {
        val message = when {
            args.isNotEmpty() -> getApplication<Application>().getString(messageId, args)
            else -> getApplication<Application>().getString(messageId)
        }
        snackbarEvent.invoke(SnackBarMessage(message))
    }


}