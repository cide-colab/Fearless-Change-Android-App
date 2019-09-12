package de.thkoeln.colab.fearlesschange.view.favorites

import android.app.Application
import de.thkoeln.colab.fearlesschange.core.pattern.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInfo

class FavoritesViewModel(application: Application) : BasicPatternViewModel(application) {

    val pattern = patternRepository.getFavoritesInfo()

    val toggleFavorite: (PatternInfo) -> Unit = { patternRepository.switchFavorite(it.pattern.id) }

    val patternCardClicked: (PatternInfo?) -> Unit = { patternInfo ->
        patternInfo?.let { info ->
            notifyPatternClicked(info)
            notifyAction(FavoritesFragmentDirections.actionNavFavoritesToPatternDetailSwipeFragment(pattern.value?.map { it.pattern.id }?.toLongArray() ?: longArrayOf(), info.pattern.id))
        }
    }

}
