package de.thkoeln.fherborn.fearlesschange.databases.repositories

import android.app.Application
import de.thkoeln.fherborn.fearlesschange.databases.CardDatabase

/**
 * Created by florianherborn on 06.08.18.
 */
class CardRepository(application: Application?) {

    private val dao = CardDatabase.getInstance(application
            ?: throw RuntimeException("Application is null")).cardDao()

    fun getAll() = dao.getAll()
    fun getById(id: Long) = dao.getById(id)
    fun getRandom(count: Int) = dao.getRandom(count)
    fun getCount() = dao.getCount()
    fun getElementWithIndex(index: Long) = dao.getElementWithIndex(index)
    fun getFavorites() = dao.getFavorites()
}