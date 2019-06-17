package de.thkoeln.colab.fearlesschange.ui.pattern.swiper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.ui.BasicPatternViewModel

@Suppress("UNCHECKED_CAST")
class PatternDetailSwiperViewModelFactory(private val application: Application, private val args: PatternDetailSwiperFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternDetailSwiperViewModel(application, args) as T
    }
}

class PatternDetailSwiperViewModel(application: Application, private var args: PatternDetailSwiperFragmentArgs) : BasicPatternViewModel(application) {

    //val pattern: LiveData<List<PatternInfo>> = patternRepository.getInfos(args.patternIds.toList())

    //TODO pass id on return
    // private var currentPatternId = args.currentPatternId
    val currentPosition: Int = args.patternIds.indexOfFirst { it == args.currentPatternId }
    val patternIds = args.patternIds
    //fun getCurrentPatternIndex() = pattern.value?.let { pattern -> pattern.indexOfFirst { it.pattern.id == currentPatternId } }
//
//
//    val sharePatternEvent = SingleActionLiveData<Pattern>()
//
//    private val currentPatternDynamic = DynamicLiveData<PatternInfo>()
//    val currentPattern = currentPatternDynamic.asLiveData()

//    fun onSwipePager(position: Int) {
//        currentPatternId = args.patternIds[position]
//        currentPatternDynamic.newSource(patternRepository.getInfo(currentPatternId))
//    }

//    val sharePressed: () -> Unit = {
//        currentPattern.value?.pattern
//                ?.let { sharePatternEvent.invoke(it) }
//                ?:notify(R.string.massage_no_pattern_to_share)
//    }
}
