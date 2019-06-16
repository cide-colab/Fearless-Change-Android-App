package de.thkoeln.colab.fearlesschange.data.viewmodel

import android.app.Application
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.note.Note
import de.thkoeln.colab.fearlesschange.data.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.helper.SnackBarMessage
import de.thkoeln.colab.fearlesschange.helper.events.ActionLiveData

class NoteViewModel(context: Application) : BasicViewModel(context) {

    private val noteRepository by lazy { NoteRepository(context) }

    val openCreateNoteDialogEvent = ActionLiveData<Long>()

    fun createNoteConfirmed(patternId: Long?, title: String, text: String) {
        val note = Note(title = title, text = text, patternId = forceGetNonNullId(patternId))
        noteRepository.insert(note)
    }

    fun deleteNoteConfirmed(note: Note) {
        noteRepository.delete(note)
        val message = getApplication<Application>().getString(R.string.message_note_deleted, note.title)
        sendSnackBarMessageEvent.invoke(SnackBarMessage(message))
    }

    fun getNotesForPattern(patternId: Long?) = noteRepository.getNotesForPattern(forceGetNonNullId(patternId))

    fun addNoteButtonClicked(patternId: Long?) {
        openCreateNoteDialogEvent.invoke(forceGetNonNullId(patternId))
    }

}