package de.thkoeln.colab.fearlesschange.view.pattern.preview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.thkoeln.colab.fearlesschange.core.pattern.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData

class PatternPreviewViewModel(application: Application, args: PatternPreviewFragmentArgs) : BasicPatternViewModel(application) {

    val pattern = patternRepository.getInfo(args.patternId)

    val patternCardClicked: (PatternPreviewData?) -> Unit = { patternInfo ->
        patternInfo?.let {
            notifyPatternClicked(patternInfo)
            notifyAction(PatternPreviewFragmentDirections.actionPatternPreviewFragmentToPatternDetailSwipeFragment(longArrayOf(it.pattern.id), it.pattern.id))
        }
    }

}


@Suppress("UNCHECKED_CAST")
class PatternPreviewViewModelFactory(private val application: Application, private val args: PatternPreviewFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatternPreviewViewModel(application, args) as T
    }
}