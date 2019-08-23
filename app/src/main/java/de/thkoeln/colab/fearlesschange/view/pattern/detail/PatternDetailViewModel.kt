package de.thkoeln.colab.fearlesschange.view.pattern.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.events.SingleActionLiveData
import de.thkoeln.colab.fearlesschange.core.pattern.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.view.pattern.swiper.PatternDetailSwiperFragmentDirections

class PatternDetailViewModel(application: Application, args: PatternDetailFragmentArgs) : BasicPatternViewModel(application) {

    val patternId = args.patternId
    val pattern = patternRepository.getInfo(patternId)
    val favButtonIcon: Int
        get() = if (pattern.value?.pattern?.favorite == true) R.drawable.ic_favorite_full else R.drawable.ic_favorite_primary

    val sharePatternEvent = SingleActionLiveData<Pattern>()

    val favoriteButtonPressed: () -> Unit = {
        patternRepository.switchFavorite(patternId)
    }

    val sharePressed: () -> Unit = {
        pattern.value?.pattern
                ?.let { sharePatternEvent.invoke(it) }
                ?: notify(R.string.massage_no_pattern_to_share)
    }

    fun showNodesBtnClicked() {
        notifyAction(PatternDetailSwiperFragmentDirections.actionPatternDetailSwipeFragmentToPatternNotesFragment(patternId))
    }

}

@Suppress("UNCHECKED_CAST")
class PatternDetailViewModelFactory(private val application: Application, private val args: PatternDetailFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternDetailViewModel(application, args) as T
    }
}