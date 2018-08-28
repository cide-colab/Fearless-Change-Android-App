package de.thkoeln.fherborn.fearlesschange.data.viewmodel

import android.app.Application
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.persistance.note.NoteRepository
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternRepository

class SettingsViewModel(context: Application) : BasicViewModel(context) {

    private val noteRepository by lazy { NoteRepository(context) }
    private val patternRepository by lazy { PatternRepository(context) }

    fun resetMostClickedPatternClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_note) {
            noteRepository.deleteAll()
            sendMessage(R.string.message_notes_reset)
        }
    }

    fun resetFavoritesClicked() {
        requestConfirmation(R.string.message_request_confirmation_reset_favorites) {
            patternRepository.setAllFavorites(false)
            sendMessage(R.string.message_favorites_reset)
        }
    }

    fun resetToFactorySettingsClicked() {
        resetFavoritesClicked()
        resetMostClickedPatternClicked()
    }

}