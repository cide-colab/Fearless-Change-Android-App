package de.thkoeln.fherborn.fearlesschange.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import de.thkoeln.fherborn.fearlesschange.v2.events.SingleLiveEvent
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardWithNoteCount
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository

class CardViewModel(application: Application) : AndroidViewModel(application) {

    val createNewNoteEvent = SingleLiveEvent<Void>()

    private val cardRepository by lazy {
        CardRepository(application)
    }


    fun getCardOfTheDay(): LiveData<CardWithNoteCount> =
            Transformations.switchMap(cardRepository.getCount()) {
                calculateCardOfTheDayIndex(it)?.let { cardIndex ->
                    cardRepository.getElementWithIndexWithNoteCount(cardIndex)
                }
            }

    private fun calculateCardOfTheDayIndex(countOfCards: Long?): Long? {
        val currentDay = System.currentTimeMillis() / 1000 / 60 / 60 / 24
        return when (countOfCards) {
            null, 0L -> null
            else -> currentDay % countOfCards
        }
    }

}