package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.doAsync
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.AppDatabase


/**
 * Created by florianherborn on 06.08.18.
 */
class StatisticRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val dao = database.statisticDao()



    fun insert(vararg cardStatisticAction: Statistic) = doAsync { dao.insert(*cardStatisticAction) }

    fun getAll() = dao.getAll()

    fun get(id: Long) = dao.get(id)

    fun getCount() = dao.getCount()

    fun getActionCount(action: StatisticAction) = dao.getActionCount(action)

    fun getMostCommonByAction(action: StatisticAction) = dao.getMostCommonByAction(action)

}