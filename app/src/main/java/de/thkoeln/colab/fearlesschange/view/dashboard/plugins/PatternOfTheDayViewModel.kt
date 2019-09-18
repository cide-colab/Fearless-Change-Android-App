package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.app.Application
import androidx.lifecycle.LiveData
import de.thkoeln.colab.fearlesschange.core.extensions.switchMap
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.view.dashboard.DashboardFragmentDirections

class PatternOfTheDayViewModel(application: Application) : InteractiveViewModel(application) {

    private val patternRepository by lazy { PatternRepository(application) }

    val patternOfTheDayData: LiveData<PatternPreviewData> = patternRepository.getAllIds().switchMap { ids ->
        calculatePatternOfTheDay(ids)?.let { id -> patternRepository.getInfo(id) }
    }

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo.pattern)
            navigator {
                navigate(DashboardFragmentDirections.actionNavDashboardToPatternDetailFragment(longArrayOf(it.pattern.id), it.pattern.id))
            }
        }
    }

    private fun calculatePatternOfTheDay(cardIds: List<Long>): Long? {
        val currentDay = (System.currentTimeMillis() / 1000 / 60 / 60 / 24)
        return when (cardIds.size) {
            0 -> null
            else -> cardIds[(currentDay % cardIds.size).toInt()]
        }
    }
}

