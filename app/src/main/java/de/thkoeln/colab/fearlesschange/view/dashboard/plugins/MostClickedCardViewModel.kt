package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.app.Application
import androidx.lifecycle.LiveData
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticAction
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticRepository
import de.thkoeln.colab.fearlesschange.view.dashboard.DashboardFragmentDirections

class MostClickedCardViewModel(application: Application) : InteractiveViewModel(application) {

    private val statisticRepository by lazy { StatisticRepository(application) }

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo.pattern)
            navigator {
                navigate(DashboardFragmentDirections.actionNavDashboardToPatternDetailFragment(longArrayOf(it.pattern.id), it.pattern.id))
            }
        }
    }

    val mostClickedPatternData: LiveData<PatternPreviewData?> = statisticRepository.getMostCommonByAction(StatisticAction.CLICK)

}
