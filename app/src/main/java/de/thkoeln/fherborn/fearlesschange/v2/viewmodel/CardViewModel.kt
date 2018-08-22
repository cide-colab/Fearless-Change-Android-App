package de.thkoeln.fherborn.fearlesschange.v2.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatistic
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatisticAction
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.StatisticRepository
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.NoteRepository
import de.thkoeln.fherborn.fearlesschange.v2.events.Event

/**
 * Created by florianherborn on 22.08.18.
 */
class CardViewModel(context: Application): AndroidViewModel(context) {

    private val cardRepository by lazy { CardRepository(context) }
    private val noteRepository by lazy { NoteRepository(context) }
    private val statisticRepository by lazy { StatisticRepository(context) }

    val cardClickedEvent = Event<Long>()


    fun getCard(id: Long?) = cardRepository.get(getCheckedCardId(id))
    fun getCards() = cardRepository.getAll()
    fun getFavorites() = cardRepository.getFavorites()
    fun getNoteCountOfCard(id: Long?) = noteRepository.getNoteCountByCardId(getCheckedCardId(id))
    fun createNote(cardId: Long?, title: String, description: String) {
        noteRepository.insert(Note(title = title, description = description, cardId = getCheckedCardId(cardId)))
    }
    fun getNotes(cardId: Long?) = noteRepository.getNotesForCard(getCheckedCardId(cardId))
    fun switchFavorite(cardId: Long?) {
        cardRepository.switchFavorite(getCheckedCardId(cardId))
    }
    fun getCardOfTheDay() = Transformations.switchMap(cardRepository.getAllCardIds()) { ids ->
        calculateCardOfTheDay(ids)?.let { id -> cardRepository.get(id) }
    }

    fun getRandomCards(count: Int) = Transformations.switchMap(cardRepository.getAllCardIds()) { ids ->
        cardRepository.get(calculateRandomCards(ids, count))
    }

    fun getMostClickedCard(): LiveData<Card> = Transformations.switchMap(statisticRepository.getMostAction(CardStatisticAction.CLICK)) {
        it?.cardId?.let { cardRepository.get(it) }
    }

    private fun calculateRandomCards(ids: List<Long>, count: Int) =
        ids.shuffled().subList(0, Math.min(count, ids.size))

    private fun calculateCardOfTheDay(cardIds: List<Long>): Long? {
        val currentDay = (System.currentTimeMillis() / 1000 / 60 / 60 / 24)
        return when (cardIds.size) {
            0 -> null
            else -> cardIds[(currentDay %cardIds.size).toInt()]
        }
    }

    fun deleteNote(note: Note) {
        noteRepository.delete(note)
    }

    private fun getCheckedCardId(id: Long?) = id?: throw IllegalArgumentException("Missing card id")

    fun cardClicked(card: Card?) {
        getCheckedCardId(card?.id).let {
            statisticRepository.insert(CardStatistic(cardId = it, action = CardStatisticAction.CLICK))
            cardClickedEvent.invoke(it)
        }
    }

}