package de.thkoeln.colab.fearlesschange.view.overview

import android.app.Application
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository

class CardsViewModel(application: Application) : InteractiveViewModel(application) {


    private val patternRepository by lazy { PatternRepository(application) }
    val pattern = patternRepository.getAllInfo()

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let { info ->
            notifyPatternClicked(info.pattern)
            navigator {
                navigate(CardsFragmentDirections.actionNavCardsToPatternDetailSwipeFragment(pattern.value?.map { it.pattern.id }?.toLongArray()
                        ?: longArrayOf(), info.pattern.id))
            }
        }
    }

}
