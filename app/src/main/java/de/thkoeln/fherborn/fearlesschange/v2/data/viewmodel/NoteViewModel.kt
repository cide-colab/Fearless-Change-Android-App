package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.Note
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.NoteRepository
import de.thkoeln.fherborn.fearlesschange.v2.helper.SnackBarMessage
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event

class NoteViewModel(context: Application) : AndroidViewModel(context) {

    private val noteRepository by lazy { NoteRepository(context) }

    val requestNewNoteEvent = Event<Long>()
    val noteCreatedEvent = Event<Note>()
    val sendSnackBarMessageEvent = Event<SnackBarMessage>()
    val openCreateNoteDialogEvent = Event<Unit>()

    fun addNoteRequested(patternId: Long?) {
        requestNewNoteEvent.invoke(getCheckedPatternId(patternId))
    }

    fun createNoteConfirmed(patternId: Long?, title: String, text: String) {
        val note = Note(title = title, text = text, patternId = getCheckedPatternId(patternId))
        noteRepository.insert(note)
        noteCreatedEvent.invoke(note)
    }

    fun deleteNoteConfirmed(note: Note) {
        noteRepository.delete(note)
        val message = getApplication<Application>().getString(R.string.noteDeleted, note.title)
        sendSnackBarMessageEvent.invoke(SnackBarMessage(message))
    }

    fun getNotesForPattern(patternId: Long?) = noteRepository.getNotesForPattern(getCheckedPatternId(patternId))

    private fun getCheckedPatternId(id: Long?) = id
            ?: throw IllegalArgumentException("Missing pattern id")

    fun addNoteButtonClicked(patternId: Long?) {
        requestNewNoteEvent.invoke(getCheckedPatternId(patternId))
    }

}