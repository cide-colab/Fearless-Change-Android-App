package de.thkoeln.fherborn.fearlesschange.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
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

    private val _noteCreatedEvent = MutableLiveData<Note>()
    val noteCreatedEvent: LiveData<Note>
        get() = _noteCreatedEvent

    private val _noteRemovedEvent = MutableLiveData<Note>()
    val noteRemovedEvent: LiveData<Note>
        get() = _noteRemovedEvent

    private fun getCardId(): Long = cardId ?: throw IllegalArgumentException("CardId is null")

    fun getRawNote() =
            Note(title = "", description = "", cardId = getCardId())

    fun createNote(note: Note) {
        noteRepository.insert(note)
        _noteCreatedEvent.value = note
    }

    fun removeNote(note: Note) {
        noteRepository.delete(note)
        _noteRemovedEvent.value = note
    }

    private fun getAllNotes() = noteRepository.getByCardId(getCardId())
}

