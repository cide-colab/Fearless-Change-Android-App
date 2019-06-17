package de.thkoeln.colab.fearlesschange.ui.plugins

import android.app.Application
import androidx.lifecycle.LiveData
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.switchMap
import de.thkoeln.colab.fearlesschange.ui.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.ui.dashboard.DashboardFragmentDirections

class PatternOfTheDayViewModel(application: Application) : BasicPatternViewModel(application) {

    val patternOfTheDay: LiveData<PatternInfo> = patternRepository.getAllIds().switchMap { ids ->
        calculatePatternOfTheDay(ids)?.let { id -> patternRepository.getInfo(id) }
    }

    val patternCardClicked: (PatternInfo?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo)
            notifyAction(DashboardFragmentDirections.actionNavDashboardToPatternDetailFragment(longArrayOf(it.pattern.id), it.pattern.id))
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

