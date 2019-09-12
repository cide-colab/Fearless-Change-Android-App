package de.thkoeln.colab.fearlesschange.view.settings

import android.app.Application
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.pattern.BasicPatternViewModel
import de.thkoeln.colab.fearlesschange.persistance.label.LabelRepository
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticAction
import kotlinx.coroutines.runBlocking

class MoreViewModel(application: Application) : BasicPatternViewModel(application) {

    private val noteRepository by lazy { NoteRepository(application) }
    private val labelRepository by lazy { LabelRepository(application) }

    fun resetMostClickedPatternClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_most_clicked_pattern) {
            statisticRepository.deleteAllByAction(StatisticAction.CLICK)
            notify(R.string.message_most_clicked_pattern_reset)
        }
    }

    fun resetFavoritesClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_favorites) {
            patternRepository.setAllFavorites(false)
            notify(R.string.message_favorites_reset)
        }
    }

    fun resetNotesClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_note) {
            runBlocking {
                noteRepository.deleteAll()
            }
            notify(R.string.message_notes_reset)
        }
    }

    fun resetLabelsClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_labels) {
            runBlocking {
                labelRepository.deleteAll()
            }
            notify(R.string.message_labels_reset)
        }
    }

    fun resetToFactorySettingsClicked() {
        resetFavoritesClicked()
        resetMostClickedPatternClicked()
        resetNotesClicked()
        resetLabelsClicked()
    }
}
