package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.app.Application
import androidx.lifecycle.LiveData
import de.thkoeln.colab.fearlesschange.core.pattern.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticAction
import de.thkoeln.colab.fearlesschange.view.dashboard.DashboardFragmentDirections

class MostClickedCardViewModel(application: Application) : BasicPatternViewModel(application) {

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo)
            notifyAction(DashboardFragmentDirections.actionNavDashboardToPatternDetailFragment(longArrayOf(it.pattern.id), it.pattern.id))
        }
    }

    val mostClickedPatternData: LiveData<PatternPreviewData?> = statisticRepository.getMostCommonByAction(StatisticAction.CLICK)

}
