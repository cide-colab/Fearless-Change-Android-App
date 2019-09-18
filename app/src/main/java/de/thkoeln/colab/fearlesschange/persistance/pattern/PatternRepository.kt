package de.thkoeln.colab.fearlesschange.persistance.pattern

import android.content.Context
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase

/**
 * Created by florianherborn on 06.08.18.
 */
class PatternRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val dao = database.patternDao()

    fun getAllInfo() = dao.getAllInfo()
    fun getAll() = dao.getAll()
    suspend fun get(id: Long) = dao.get(id)
    fun get(ids: List<Long>) = dao.get(ids)
    fun getInfo(id: Long) = dao.getInfo(id)
    fun getInfos(ids: List<Long>) = dao.getInfos(ids)
    fun getCount() = dao.getCount()
    fun getFavorites() = dao.getFavorites()
    fun getFavoritesInfo() = dao.getFavoritesInfo()
    suspend fun switchFavorite(id: Long) = dao.switchFavorite(id)
    fun getAllIds() = dao.getAllIds()
    fun getRandom(count: Int) = dao.getRandom(count)
    suspend fun setAllFavorites(flag: Boolean) = dao.setAllFavorites(flag)
    suspend fun getLike(query: String) = dao.getLike(query)
}