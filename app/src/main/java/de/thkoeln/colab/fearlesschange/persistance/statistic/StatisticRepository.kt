package de.thkoeln.colab.fearlesschange.persistance.statistic

import android.content.Context
import de.thkoeln.colab.fearlesschange.core.doAsync
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase


/**
 * Created by florianherborn on 06.08.18.
 */
class StatisticRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val dao = database.statisticDao()

    fun insert(vararg actions: Statistic) = doAsync { dao.insert(*actions) }

    fun getAll() = dao.getAll()

    fun get(id: Long) = dao.get(id)

    fun getCount() = dao.getCount()

    fun getActionCount(action: StatisticAction) = dao.getActionCount(action)

    fun getMostCommonByAction(action: StatisticAction) = dao.getMostCommonByAction(action)

    fun deleteAllByAction(action: StatisticAction) = doAsync { dao.deleteByAction(action) }

}