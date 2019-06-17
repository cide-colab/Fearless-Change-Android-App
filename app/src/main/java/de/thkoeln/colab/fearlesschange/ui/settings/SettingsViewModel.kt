package de.thkoeln.colab.fearlesschange.ui.settings

import android.app.Application
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.data.persistance.statistic.StatisticAction
import de.thkoeln.colab.fearlesschange.ui.BasicPatternViewModel

class SettingsViewModel(application: Application) : BasicPatternViewModel(application) {

    private val noteRepository by lazy { NoteRepository(application) }

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
            noteRepository.deleteAll()
            notify(R.string.message_notes_reset)
        }
    }

    fun resetToFactorySettingsClicked() {
        resetFavoritesClicked()
        resetMostClickedPatternClicked()
        resetNotesClicked()
    }
}