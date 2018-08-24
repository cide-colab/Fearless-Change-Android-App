package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.NoteRepository
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternRepository

class SettingsViewModel(context: Application) : BasicViewModel(context) {

    private val noteRepository by lazy { NoteRepository(context) }
    private val patternRepository by lazy { PatternRepository(context) }

    fun resetMostClickedPatternClicked() {
        requestConfirmation(R.string.request_confirmation_reset_note_message) {
            noteRepository.deleteAll()
            sendMessage(R.string.message_notes_resetted)
        }
    }

    fun resetFavoritesClicked() {

    }

    fun resetToFactorySettingsClicked() {

    }

}