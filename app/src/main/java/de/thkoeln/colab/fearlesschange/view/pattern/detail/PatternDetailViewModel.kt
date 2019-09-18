package de.thkoeln.colab.fearlesschange.view.pattern.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.events.SingleActionLiveData
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.view.pattern.swiper.PatternDetailSwiperFragmentDirections
import de.thkoeln.colab.fearlesschange.view.patternData.detail.PatternDetailFragmentArgs
import kotlinx.coroutines.runBlocking

class PatternDetailViewModel(application: Application, args: PatternDetailFragmentArgs) : InteractiveViewModel(application) {


    private val patternRepository by lazy { PatternRepository(application) }
    val patternId = args.patternId
    val pattern = patternRepository.getInfo(patternId)
    val favButtonIcon: Int
        get() = if (pattern.value?.pattern?.favorite == true) R.drawable.ic_toolbar_favorite_full else R.drawable.ic_toolbar_favorite

    val sharePatternEvent = SingleActionLiveData<Pattern>()

    val favoriteButtonPressed: () -> Unit = {
        runBlocking {
            patternRepository.switchFavorite(patternId)
        }
    }

    val sharePressed: () -> Unit = {
        pattern.value?.pattern
                ?.let { sharePatternEvent.invoke(it) }
                ?: snackBar {
                    setText(R.string.massage_no_pattern_to_share)
                }
    }

    fun showNodesBtnClicked() {
        navigator {
            navigate(PatternDetailSwiperFragmentDirections.actionPatternDetailSwipeFragmentToPatternNotesFragment(patternId))
        }
    }

    fun createNoteButtonClicked() {
        navigator {
            navigate(PatternDetailSwiperFragmentDirections.actionPatternDetailSwipeFragmentToCreateNoteFragment(patternId))
        }
    }

}

@Suppress("UNCHECKED_CAST")
class PatternDetailViewModelFactory(private val application: Application, private val args: PatternDetailFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternDetailViewModel(application, args) as T
    }
}