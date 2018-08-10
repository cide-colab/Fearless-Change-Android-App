package de.thkoeln.fherborn.fearlesschange.persistance.repositories

import android.app.Application
import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.CardDatabase
import de.thkoeln.fherborn.fearlesschange.persistance.models.Action
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardAction
import de.thkoeln.fherborn.fearlesschange.persistance.runInBackground


/**
 * Created by florianherborn on 06.08.18.
 */
class CardActionRepository(context: Context?) {

    private val dao = CardDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).cardActionDao()

    fun insert(vararg cardAction: CardAction) = runInBackground { dao.insert(*cardAction) }

    fun getAll() = dao.getAll()

    fun getById(id: Long) = dao.getById(id)

    fun getCount() = dao.getCount()

    fun getCountOfAction(action: Action) = dao.getCountOfAction(action)

    fun getMostByAction(action: Action) = dao.getMostByAction(action)
}