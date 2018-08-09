package de.thkoeln.fherborn.fearlesschange.persistance.repositories

import android.app.Application
import de.thkoeln.fherborn.fearlesschange.persistance.CardDatabase
import de.thkoeln.fherborn.fearlesschange.persistance.models.Action
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardAction
import android.os.AsyncTask
import de.thkoeln.fherborn.fearlesschange.persistance.daos.CardActionDao


/**
 * Created by florianherborn on 06.08.18.
 */
class CardActionRepository(application: Application?) {

    private val dao = CardDatabase.getInstance(application
            ?: throw RuntimeException("Application is null")).cardActionDao()

    fun insert(vararg cardAction: CardAction) = AddActionAsyncTask(dao).execute(*cardAction)

    fun getAll() = dao.getAll()

    fun getById(id: Long) = dao.getById(id)

    fun getCount() = dao.getCount()

    fun getCountOfAction(action: Action) = dao.getCountOfAction(action)

    fun getMostByAction(action: Action) = dao.getMostByAction(action)

    private class AddActionAsyncTask(private val dao: CardActionDao) : AsyncTask<CardAction, Void, Void>() {

        override fun doInBackground(vararg params: CardAction): Void? {
            dao.insert(*params)
            return null
        }
    }
}