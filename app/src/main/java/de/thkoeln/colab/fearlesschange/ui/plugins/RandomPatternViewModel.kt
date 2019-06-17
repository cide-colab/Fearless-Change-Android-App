package de.thkoeln.colab.fearlesschange.ui.plugins

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.asLiveData
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.helper.events.DynamicLiveData
import de.thkoeln.colab.fearlesschange.map
import de.thkoeln.colab.fearlesschange.ui.dashboard.DashboardFragmentDirections


@Suppress("UNCHECKED_CAST")
class RandomPatternViewModelFactory(private val application: Application, private val args: RandomPatternFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RandomPatternViewModel(application, args) as T
    }
}


class RandomPatternViewModel(application: Application, private val args: RandomPatternFragmentArgs) : BasicPatternViewModel(application) {

    private val cachedPattern = hashMapOf<Int, Triple<PatternInfo, PatternInfo, PatternInfo>?>()

    val patternCardClicked: (PatternInfo?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo)
            notifyAction(DashboardFragmentDirections.actionNavDashboardToPatternDetailFragment(getPatternForDetail(), it.pattern.id))
        }
    }

    private fun getPatternForDetail() =
            listOfNotNull(getSavedPattern()?.first?.pattern?.id, getSavedPattern()?.second?.pattern?.id, getSavedPattern()?.third?.pattern?.id).toLongArray()

    val shuffleClicked: () -> Unit = {
        shuffle()
    }

    var shouldAnimatePattern = false
        private set

    private val randomPatternDynamic = DynamicLiveData<Triple<PatternInfo, PatternInfo, PatternInfo>?>()
    val randomPattern = randomPatternDynamic.asLiveData()

    init {
        getSavedPattern()?.let { randomPatternDynamic.postValue(it) } ?: shuffle()
    }

    private fun shuffle() {
        shouldAnimatePattern = true
        randomPatternDynamic.newSource(getNewRandomPattern()).then { pattern ->
            pattern?.let { cachePattern(it) }
            shouldAnimatePattern = false
        }
    }

    private fun getNewRandomPattern(): LiveData<Triple<PatternInfo, PatternInfo, PatternInfo>?> {
        return patternRepository.getRandom(3).map { list ->
            if (list.size < 0) return@map null
            Triple(list[0], list[1], list[2]).also { cachePattern(it) }
        }
    }

    private fun getSavedPattern() = cachedPattern[args.groupId]
    private fun cachePattern(triple: Triple<PatternInfo, PatternInfo, PatternInfo>) {
        cachedPattern[args.groupId] = triple
    }
}
