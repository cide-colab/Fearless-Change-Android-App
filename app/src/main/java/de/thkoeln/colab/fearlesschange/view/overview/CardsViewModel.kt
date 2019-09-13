package de.thkoeln.colab.fearlesschange.view.overview

import android.app.Application
import de.thkoeln.colab.fearlesschange.core.pattern.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData

class CardsViewModel(application: Application) : BasicPatternViewModel(application) {

    val pattern = patternRepository.getAllInfo()

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let { info ->
            notifyPatternClicked(info)
            notifyAction(CardsFragmentDirections.actionNavCardsToPatternDetailSwipeFragment(pattern.value?.map { it.pattern.id }?.toLongArray()
                    ?: longArrayOf(), info.pattern.id))
        }
    }

}
