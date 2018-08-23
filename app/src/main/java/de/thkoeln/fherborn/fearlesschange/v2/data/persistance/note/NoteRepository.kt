package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.doAsync
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.AppDatabase

/**
 * Created by Florian on 18.08.2018.
 */
class NoteRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).noteDao()

    fun insert(vararg notes: Note) = doAsync { dao.insert(*notes) }
    fun update(vararg notes: Note) = doAsync { dao.update(*notes) }
    fun delete(vararg notes: Note) = doAsync { dao.delete(*notes) }
    fun get(id: Long) = dao.get(id)
    fun getAll() = dao.getAll()
    fun getNotesForPattern(id: Long) = dao.getNotesForPattern(id)
    fun getCount() = dao.getCount()

}