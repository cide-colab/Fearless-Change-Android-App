package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.AppDatabase
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.doAsync

/**
 * Created by florianherborn on 06.08.18.
 */
class PatternRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val dao = database.patternDao()

    fun getAll() = dao.getAll()
    fun getAllInfo() = dao.getAllInfo()
    fun get(id: Long) = dao.get(id)
    fun get(ids: List<Long>) = dao.get(ids)
    fun getInfo(id: Long) = dao.getInfo(id)
    fun getInfos(ids: List<Long>) = dao.getInfos(ids)
    fun getCount() = dao.getCount()
    fun getFavorites() = dao.getFavorites()
    fun getFavoritesInfo() = dao.getFavoritesInfo()
    fun switchFavorite(id: Long) = doAsync { dao.switchFavorite(id) }
    fun getAllIds() = dao.getAllIds()
    fun getRandom(count: Int) = dao.getRandom(count)
}