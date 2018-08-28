package de.thkoeln.fherborn.fearlesschange.data.viewmodel

import android.app.Application
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.persistance.note.NoteRepository
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternRepository
import de.thkoeln.fherborn.fearlesschange.data.persistance.statistic.StatisticAction
import de.thkoeln.fherborn.fearlesschange.data.persistance.statistic.StatisticRepository

class SettingsViewModel(context: Application) : BasicViewModel(context) {

    private val noteRepository by lazy { NoteRepository(context) }
    private val patternRepository by lazy { PatternRepository(context) }
    private val statisticRepository by lazy { StatisticRepository(context) }

    fun resetMostClickedPatternClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_most_clicked_pattern) {
            statisticRepository.deleteAllByAction(StatisticAction.CLICK)
            sendMessage(R.string.message_most_clicked_pattern_reset)
        }
    }

    fun resetFavoritesClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_favorites) {
            patternRepository.setAllFavorites(false)
            sendMessage(R.string.message_favorites_reset)
        }
    }

    fun resetNotesClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_note) {
            noteRepository.deleteAll()
            sendMessage(R.string.message_notes_reset)
        }
    }

    fun resetToFactorySettingsClicked() {
        resetFavoritesClicked()
        resetMostClickedPatternClicked()
        resetNotesClicked()
    }

}