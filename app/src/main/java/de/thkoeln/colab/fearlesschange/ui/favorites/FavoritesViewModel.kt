package de.thkoeln.colab.fearlesschange.ui.favorites

import android.app.Application
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.ui.plugins.BasicPatternViewModel

class FavoritesViewModel(application: Application) : BasicPatternViewModel(application) {

    val pattern = patternRepository.getFavoritesInfo()

    val patternDeleted: (PatternInfo) -> Unit = { patternRepository.switchFavorite(it.pattern.id) }

    val patternCardClicked: (PatternInfo?) -> Unit = { patternInfo ->
        patternInfo?.let { info ->
            notifyPatternClicked(info)
            notifyAction(FavoritesFragmentDirections.actionNavFavoritesToPatternDetailSwipeFragment(pattern.value?.map { it.pattern.id }?.toLongArray() ?: longArrayOf(), info.pattern.id))
        }
    }

}
