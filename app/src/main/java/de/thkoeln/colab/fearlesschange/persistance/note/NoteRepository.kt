package de.thkoeln.colab.fearlesschange.persistance.note

import android.content.Context
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase

/**
 * Created by Florian on 18.08.2018.
 */
class NoteRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).noteDao()

    suspend fun insert(notes: Note) = dao.insert(notes)
    suspend fun update(notes: Note) = dao.update(notes)
    suspend fun delete(notes: Note) = dao.delete(notes)
    suspend fun deleteAll() = dao.deleteAll()
    fun get(id: Long) = dao.get(id)
    suspend fun getLike(query: String) = dao.getLike(query)
    suspend fun getAll() = dao.getAll()
    suspend fun getNotesForPattern(id: Long) = dao.getNotesForPattern(id)
    fun getCount() = dao.getCount()
}