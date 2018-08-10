package de.thkoeln.fherborn.fearlesschange.persistance.repositories

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.CardDatabase
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.runInBackground

/**
 * Created by florianherborn on 06.08.18.
 */
class CardRepository(context: Context?) {

    private val dao = CardDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).cardDao()

    fun getAll() = dao.getAll()
    fun getById(id: Long) = dao.getById(id)
    fun getRandom(count: Int) = dao.getRandom(count)
    fun getCount() = dao.getCount()
    fun getElementWithIndex(index: Long) = dao.getElementWithIndex(index)
    fun getFavorites() = dao.getFavorites()
    fun update(vararg cards: Card) = runInBackground { dao.update(*cards) }
}