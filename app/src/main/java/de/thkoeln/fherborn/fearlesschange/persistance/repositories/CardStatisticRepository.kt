package de.thkoeln.fherborn.fearlesschange.persistance.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.CardDatabase
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatisticAction
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatistic
import de.thkoeln.fherborn.fearlesschange.persistance.doAsync


/**
 * Created by florianherborn on 06.08.18.
 */
class CardStatisticRepository(context: Context?) {

    private val database = CardDatabase.getInstance(context
            ?: throw RuntimeException("Application is null"))
    private val cardActionDao = database.cardActionDao()
    private val cardDao = database.cardDao()

    fun insert(vararg cardStatistics: CardStatistic) = doAsync { cardActionDao.insert(*cardStatistics) }

    fun getAll() = cardActionDao.getAll()

    fun getById(id: Long) = cardActionDao.getById(id)

    fun getCount() = cardActionDao.getCount()

    fun getCountOfAction(action: CardStatisticAction) = cardActionDao.getCountOfAction(action)

    fun getCardByMostAction(action: CardStatisticAction): LiveData<Card> = Transformations.switchMap(cardActionDao.getMostByAction(action)) {
        it?.let { cardDao.getById(it.cardId) }
    }
}