package de.thkoeln.colab.fearlesschange.ui.plugins

import android.app.Application
import androidx.lifecycle.LiveData
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.data.persistance.statistic.StatisticAction
import de.thkoeln.colab.fearlesschange.ui.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.ui.dashboard.DashboardFragmentDirections

class MostClickedCardViewModel(application: Application) : BasicPatternViewModel(application) {

    val patternCardClicked: (PatternInfo?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo)
            notifyAction(DashboardFragmentDirections.actionNavDashboardToPatternDetailFragment(longArrayOf(it.pattern.id), it.pattern.id))
        }
    }

    val mostClickedPattern: LiveData<PatternInfo?> = statisticRepository.getMostCommonByAction(StatisticAction.CLICK)

}
