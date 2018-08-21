package de.thkoeln.fherborn.fearlesschange.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import de.thkoeln.fherborn.fearlesschange.events.ObservableEvent
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository


class CardNoteViewModel(application: Application) : AndroidViewModel(application) {

    var cardId: Long? = null

    private val noteRepository by lazy {
        NoteRepository(application)
    }

    val cardNotes: LiveData<List<Note>> by lazy {
        getAllNotes()
    }

    val noteRemovedEvent = ObservableEvent<Note>()

    private fun getCardId(): Long = cardId ?: throw IllegalArgumentException("CardId is null")

    fun getRawNote() = Note(title = "", description = "", cardId = getCardId())

    fun createNote(note: Note) {
        noteRepository.insert(note)
    }

    fun removeNote(note: Note) {
        noteRepository.delete(note)
        noteRemovedEvent.invoke(note)
    }

    private fun getAllNotes() = noteRepository.getByCardId(getCardId())
}

