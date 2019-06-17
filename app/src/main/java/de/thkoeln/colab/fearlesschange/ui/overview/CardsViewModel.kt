package de.thkoeln.colab.fearlesschange.ui.overview

import android.app.Application
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.ui.BasicPatternViewModel

class CardsViewModel(application: Application) : BasicPatternViewModel(application) {

    val pattern = patternRepository.getAllInfo()

    val patternCardClicked: (PatternInfo?) -> Unit = { patternInfo ->
        patternInfo?.let { info ->
            notifyPatternClicked(info)
            notifyAction(CardsFragmentDirections.actionNavCardsToPatternDetailSwipeFragment(pattern.value?.map { it.pattern.id }?.toLongArray()
                    ?: longArrayOf(), info.pattern.id))
        }
    }

}
