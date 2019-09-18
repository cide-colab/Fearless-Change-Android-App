package de.thkoeln.colab.fearlesschange.core.pattern

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.core.events.SingleActionLiveData
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.persistance.statistic.Statistic
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticAction
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticRepository
import kotlinx.coroutines.runBlocking

interface StatisticLogger {
    fun notifyPatternClicked(pattern: Pattern)
}

class StatisticService(application: Application) : StatisticLogger {

    private val statisticRepository: StatisticRepository by lazy { StatisticRepository(application) }

    override fun notifyPatternClicked(pattern: Pattern) {
        runBlocking {
            statisticRepository.insert(Statistic(patternId = pattern.id, action = StatisticAction.CLICK))
        }
    }
}

typealias DialogBuilder = MaterialAlertDialogBuilder.() -> MaterialAlertDialogBuilder
typealias SnackBarBuilder = Snackbar.() -> Snackbar
typealias NavigationBuilder = NavController.() -> Unit


abstract class InteractiveViewModel(application: Application) :
        AndroidViewModel(application),
        StatisticLogger by StatisticService(application) {

    val showDialogEvent = SingleActionLiveData<DialogBuilder>()
    val showSnackBarEvent = SingleActionLiveData<SnackBarBuilder>()
    val navigationEvent = SingleActionLiveData<NavigationBuilder>()

    protected fun dialog(builder: DialogBuilder) {
        showDialogEvent.postValue(builder)
    }

    protected fun navigator(builder: NavigationBuilder) {
        navigationEvent.invoke(builder)
    }

    protected fun snackBar(builder: SnackBarBuilder) {
        showSnackBarEvent.invoke(builder)
    }
}