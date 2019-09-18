package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.core.events.DynamicLiveData
import de.thkoeln.colab.fearlesschange.core.extensions.asLiveData
import de.thkoeln.colab.fearlesschange.core.extensions.map
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.view.dashboard.DashboardFragmentDirections


@Suppress("UNCHECKED_CAST")
class RandomPatternViewModelFactory(private val application: Application, private val args: RandomPatternFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RandomPatternViewModel(application, args) as T
    }
}


class RandomPatternViewModel(application: Application, private val args: RandomPatternFragmentArgs) : InteractiveViewModel(application) {


    private val patternRepository by lazy { PatternRepository(application) }

    private val cachedPattern = hashMapOf<Int, Triple<PatternPreviewData, PatternPreviewData, PatternPreviewData>?>()

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo.pattern)
            navigator {
                navigate(DashboardFragmentDirections.actionNavDashboardToPatternDetailFragment(getPatternForDetail(), it.pattern.id))
            }
        }
    }

    private fun getPatternForDetail() =
            listOfNotNull(getSavedPattern()?.first?.pattern?.id, getSavedPattern()?.second?.pattern?.id, getSavedPattern()?.third?.pattern?.id).toLongArray()

    val shuffleClicked: () -> Unit = {
        shuffle()
    }

    var shouldAnimatePattern = false
        private set

    private val randomPatternDynamic = DynamicLiveData<Triple<PatternPreviewData, PatternPreviewData, PatternPreviewData>?>()
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

    private fun getNewRandomPattern(): LiveData<Triple<PatternPreviewData, PatternPreviewData, PatternPreviewData>?> {
        return patternRepository.getRandom(3).map { list ->
            if (list.size < 0) return@map null
            Triple(list[0], list[1], list[2]).also { cachePattern(it) }
        }
    }

    private fun getSavedPattern() = cachedPattern[args.groupId]
    private fun cachePattern(triple: Triple<PatternPreviewData, PatternPreviewData, PatternPreviewData>) {
        cachedPattern[args.groupId] = triple
    }
}
