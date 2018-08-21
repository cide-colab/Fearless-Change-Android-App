package de.thkoeln.fherborn.fearlesschange.persistance.repositories

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.persistance.CardDatabase
import de.thkoeln.fherborn.fearlesschange.persistance.doAsync
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note

/**
 * Created by Florian on 18.08.2018.
 */
class NoteRepository(context: Context?) {

    private val dao = CardDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).noteDao()

    fun insert(vararg notes: Note) = doAsync { dao.insert(*notes) }
    fun update(vararg notes: Note) = doAsync { dao.update(*notes) }
    fun delete(vararg notes: Note) = doAsync { dao.delete(*notes) }
    fun getAll() = dao.getAll()
    fun getByCardId(id: Long) = dao.getByCardId(id)
    fun getById(id: Long) = dao.getById(id)
    fun getCount() = dao.getCount()
    fun getCountByCardId(id: Long) = dao.getCountByCardId(id)

}