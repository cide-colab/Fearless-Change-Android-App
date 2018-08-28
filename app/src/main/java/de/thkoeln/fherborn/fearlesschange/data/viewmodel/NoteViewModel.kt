package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.Note
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.NoteRepository
import de.thkoeln.fherborn.fearlesschange.v2.helper.SnackBarMessage
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event

class NoteViewModel(context: Application) : BasicViewModel(context) {

    private val noteRepository by lazy { NoteRepository(context) }

    val openCreateNoteDialogEvent = Event<Long>()

    fun createNoteConfirmed(patternId: Long?, title: String, text: String) {
        val note = Note(title = title, text = text, patternId = forceGetNonNullId(patternId))
        noteRepository.insert(note)
    }

    fun deleteNoteConfirmed(note: Note) {
        noteRepository.delete(note)
        val message = getApplication<Application>().getString(R.string.noteDeleted, note.title)
        sendSnackBarMessageEvent.invoke(SnackBarMessage(message))
    }

    fun getNotesForPattern(patternId: Long?) = noteRepository.getNotesForPattern(forceGetNonNullId(patternId))

    fun addNoteButtonClicked(patternId: Long?) {
        openCreateNoteDialogEvent.invoke(forceGetNonNullId(patternId))
    }

}