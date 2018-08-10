package de.thkoeln.fherborn.fearlesschange.persistance.repositories

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.CardDatabase
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatisticAction
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatistic
import de.thkoeln.fherborn.fearlesschange.persistance.runInBackground


/**
 * Created by florianherborn on 06.08.18.
 */
class CardStatisticRepository(context: Context?) {

    private val dao = CardDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).cardActionDao()

    fun insert(vararg cardStatistics: CardStatistic) = runInBackground { dao.insert(*cardStatistics) }

    fun getAll() = dao.getAll()

    fun getById(id: Long) = dao.getById(id)

    fun getCount() = dao.getCount()

    fun getCountOfAction(action: CardStatisticAction) = dao.getCountOfAction(action)

    fun getMostByAction(action: CardStatisticAction) = dao.getMostByAction(action)
}