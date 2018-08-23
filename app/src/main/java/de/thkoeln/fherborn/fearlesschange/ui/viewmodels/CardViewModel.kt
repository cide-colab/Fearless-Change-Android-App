package de.thkoeln.fherborn.fearlesschange.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.PatternRepository
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event

class CardViewModel(application: Application) : AndroidViewModel(application) {

    val createNewNoteEvent = Event<Void>()

    private val cardRepository by lazy {
        PatternRepository(application)
    }


    fun getCardOfTheDay(): LiveData<PatternInfo> =
            Transformations.switchMap(cardRepository.getCount()) {
                calculateCardOfTheDayIndex(it)?.let { cardIndex ->
                    cardRepository.getInfo(1)
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