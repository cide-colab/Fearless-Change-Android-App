package de.thkoeln.colab.fearlesschange.view.pattern.swiper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel

@Suppress("UNCHECKED_CAST")
class PatternDetailSwiperViewModelFactory(private val application: Application, private val args: PatternDetailSwiperFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternDetailSwiperViewModel(application, args) as T
    }
}

class PatternDetailSwiperViewModel(application: Application, private var args: PatternDetailSwiperFragmentArgs) : InteractiveViewModel(application) {

    //val patternPreviewData: LiveData<List<PatternPreviewData>> = patternRepository.getInfos(args.patternIds.toList())

    //TODO pass id on return
    // private var currentPatternId = args.currentPatternId
    val currentPosition: Int = args.patternIds.indexOfFirst { it == args.currentPatternId }
    val patternIds = args.patternIds
    //fun getCurrentPatternIndex() = patternPreviewData.value?.let { patternPreviewData -> patternPreviewData.indexOfFirst { it.patternPreviewData.id == currentPatternId } }
//
//
//    val sharePatternEvent = SingleActionLiveData<Pattern>()
//
//    private val currentPatternDynamic = DynamicLiveData<PatternPreviewData>()
//    val currentPattern = currentPatternDynamic.asLiveData()

//    fun onSwipePager(position: Int) {
//        currentPatternId = args.patternIds[position]
//        currentPatternDynamic.newSource(patternRepository.getInfo(currentPatternId))
//    }

//    val sharePressed: () -> Unit = {
//        currentPattern.value?.patternPreviewData
//                ?.let { sharePatternEvent.invoke(it) }
//                ?:snackBar(R.string.massage_no_pattern_to_share)
//    }
}
