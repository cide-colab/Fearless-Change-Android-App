package de.thkoeln.fherborn.fearlesschange.v2.carddetail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository

class CardDetailViewModelFactory(
        private val context: Context?,
        private val cardId: Long
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            CardDetailViewModel(CardRepository(context), NoteRepository(context), cardId) as T
}