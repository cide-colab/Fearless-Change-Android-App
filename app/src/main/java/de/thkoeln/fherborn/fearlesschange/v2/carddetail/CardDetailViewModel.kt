package de.thkoeln.fherborn.fearlesschange.v2.carddetail

import android.arch.lifecycle.ViewModel
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository

class CardDetailViewModel(
        private val cardRepository: CardRepository,
        private val noteRepository: NoteRepository,
        private val cardId: Long
) : ViewModel() {

    val card by lazy { cardRepository.getById(cardId) }

}