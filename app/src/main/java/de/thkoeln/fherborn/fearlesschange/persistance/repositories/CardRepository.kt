package de.thkoeln.fherborn.fearlesschange.persistance.repositories

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.CardDatabase
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.doAsync

/**
 * Created by florianherborn on 06.08.18.
 */
class CardRepository(context: Context?) {

    private val database = CardDatabase.getInstance(context
            ?: throw RuntimeException("Application is null"))
    private val dao = database.cardDao()
    private val noteDao = database.noteDao()

    fun getAll() = dao.getAll()
    fun getAllWithNoteCount() = dao.getAllWithNoteCount()
    fun get(id: Long) = dao.get(id)
    fun get(ids: List<Long>) = dao.get(ids)
    fun getRandomWithNotesCount(count: Int) = dao.getRandomWithNoteCount(count)
    fun getCount() = dao.getCount()
    fun getElementWithIndexWithNoteCount(index: Long) = dao.getElementWithIndexWithNoteCount(index)
    fun getFavorites() = dao.getFavorites()
    fun getFavoritesWithNoteCount() = dao.getFavoritesWithNoteCount()
    fun update(vararg cards: Card) = doAsync { dao.update(*cards) }
    fun switchFavorite(id: Long) = doAsync { dao.switchFavorite(id) }
    fun getAllCardIds() = dao.getAllCardIds()
}