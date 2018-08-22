package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.doAsync
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.AppDatabase

/**
 * Created by florianherborn on 06.08.18.
 */
class CardRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val dao = database.cardDao()
    private val noteDao = database.noteDao()

    fun getAll() = dao.getAll()
    fun getAllInfos() = dao.getAllInfos()
    fun get(id: Long) = dao.get(id)
    fun get(ids: List<Long>) = dao.get(ids)
    fun getInfo(id: Long) = dao.getInfo(id)
    fun getInfos(ids: List<Long>) = dao.getInfos(ids)
    fun getCount() = dao.getCount()
    fun getFavorites() = dao.getFavorites()
    fun getFavoritesInfo() = dao.getFavoritesInfo()
    fun switchFavorite(id: Long) = doAsync { dao.switchFavorite(id) }
    fun getAllIds() = dao.getAllIds()
}