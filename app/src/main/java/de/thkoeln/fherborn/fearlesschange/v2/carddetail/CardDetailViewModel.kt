package de.thkoeln.fherborn.fearlesschange.v2.carddetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository

class CardDetailViewModel(
        private val cardRepository: CardRepository,
        private val noteRepository: NoteRepository,
        private val cardId: Long
) : ViewModel() {

    val card: LiveData<Card> by lazy { cardRepository.getById(cardId) }

    fun switchFavorite() {
        cardRepository.switchFavorite(cardId)
    }

    fun createNote(title: String, description: String) {
        noteRepository.insert(Note(
                title = title,
                description = description,
                cardId = cardId
        ))
    }

    fun getNotes() =
            noteRepository.getByCardId(cardId)

    fun deleteNote(note: Note) {
        noteRepository.delete(note)
    }


}