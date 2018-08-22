package de.thkoeln.fherborn.fearlesschange.v2.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import de.thkoeln.fherborn.fearlesschange.v2.helper.events.Event
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.CardInfo
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.CardRepository
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.Note
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.NoteRepository
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.Statistic
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.StatisticAction
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.StatisticRepository

/**
 * Created by florianherborn on 22.08.18.
 */
class CardViewModel(context: Application) : AndroidViewModel(context) {

    private val cardRepository by lazy { CardRepository(context) }
    private val noteRepository by lazy { NoteRepository(context) }
    private val statisticRepository by lazy { StatisticRepository(context) }

    val cardPreviewClickedEvent = Event<Long>()


    fun getCard(id: Long?) = cardRepository.get(getCheckedCardId(id))
    fun getCards() = cardRepository.getAll()
    fun getFavorites() = cardRepository.getFavorites()
    fun createNote(cardId: Long?, title: String, text: String) {
        noteRepository.insert(Note(title = title, text = text, cardId = getCheckedCardId(cardId)))
    }

    fun getNotes(cardId: Long?) = noteRepository.getNotesForCard(getCheckedCardId(cardId))
    fun switchFavorite(cardId: Long?) {
        cardRepository.switchFavorite(getCheckedCardId(cardId))
    }

    fun getCardOfTheDay(): LiveData<CardInfo> =
            Transformations.switchMap(cardRepository.getAllIds()) { ids ->
                calculateCardOfTheDay(ids)?.let { id -> cardRepository.getInfo(id) }
            }

    fun getRandomCards(count: Int): LiveData<List<CardInfo>> =
            Transformations.switchMap(cardRepository.getAllIds()) { ids ->
                cardRepository.getInfos(calculateRandomCards(ids, count))
            }

    fun getMostClickedCard(): LiveData<CardInfo> =
            Transformations.switchMap(statisticRepository.getMostCommonByAction(StatisticAction.CLICK)) {
                it?.cardId?.let { cardRepository.getInfo(it) }
            }

    fun deleteNote(note: Note) {
        noteRepository.delete(note)
    }

    fun cardPreviewClicked(cardInfo: CardInfo?) {
        getCheckedCardId(cardInfo?.card?.id).let {
            statisticRepository.insert(Statistic(cardId = it, action = StatisticAction.CLICK))
            cardPreviewClickedEvent.invoke(it)
        }
    }

    private fun getCheckedCardId(id: Long?) = id
            ?: throw IllegalArgumentException("Missing card id")

    private fun calculateRandomCards(ids: List<Long>, count: Int) =
            ids.shuffled().subList(0, Math.min(count, ids.size))

    private fun calculateCardOfTheDay(cardIds: List<Long>): Long? {
        val currentDay = (System.currentTimeMillis() / 1000 / 60 / 60 / 24)
        return when (cardIds.size) {
            0 -> null
            else -> cardIds[(currentDay % cardIds.size).toInt()]
        }
    }

}