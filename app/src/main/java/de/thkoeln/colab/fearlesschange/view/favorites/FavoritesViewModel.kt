package de.thkoeln.colab.fearlesschange.view.favorites

import android.app.Application
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository
import kotlinx.coroutines.runBlocking

class FavoritesViewModel(application: Application) : InteractiveViewModel(application) {


    private val patternRepository by lazy { PatternRepository(application) }

    val pattern = patternRepository.getFavoritesInfo()

    val toggleFavorite: (PatternPreviewData) -> Unit = {
        runBlocking {
            patternRepository.switchFavorite(it.pattern.id)
        }
    }

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let { info ->
            notifyPatternClicked(info.pattern)
            navigator {
                navigate(FavoritesFragmentDirections.actionNavFavoritesToPatternDetailSwipeFragment(pattern.value?.map { it.pattern.id }?.toLongArray()
                        ?: longArrayOf(), info.pattern.id))
            }
        }
    }

}
