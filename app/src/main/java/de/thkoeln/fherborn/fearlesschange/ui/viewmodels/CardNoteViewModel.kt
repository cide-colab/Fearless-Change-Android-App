package de.thkoeln.fherborn.fearlesschange.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository


class CardNoteViewModel(application: Application) : AndroidViewModel(application) {

    private var noteRepository = NoteRepository(application)

    private val _noteCreatedEvent = MutableLiveData<Boolean>()
    val noteCreatedEvent: LiveData<Boolean>
        get() = _noteCreatedEvent


    fun getRawNote(cardId: Long) =
        Note(title = "", description = "", cardId = cardId)

    fun createNote(note: Note) {
        noteRepository.insert(note)
        _noteCreatedEvent.value = true
    }
}

