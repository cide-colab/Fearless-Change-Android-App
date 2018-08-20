package de.thkoeln.fherborn.fearlesschange.ui.views

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository


class CreateNoteDialogViewModel(application: Application) : AndroidViewModel(application) {

    private var noteRepository = NoteRepository(application)

    private val _noteCreatedEvent = MutableLiveData<Boolean>()
    val noteCreatedEvent: LiveData<Boolean>
        get() = _noteCreatedEvent

    lateinit var note: Note

    fun initNote(cardId: Long) {
        note = Note(title = "", description = "", cardId = cardId)
    }

    private fun createNote() {
        noteRepository.insert(note)
    }

    fun onConfirm() {
        createNote()
        _noteCreatedEvent.value = true
    }
}
