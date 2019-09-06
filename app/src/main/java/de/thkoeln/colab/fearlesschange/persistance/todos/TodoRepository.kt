package de.thkoeln.colab.fearlesschange.persistance.todos

import android.content.Context
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase

class TodoRepository(context: Context?) {

    private val dao = AppDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).todoDao()

    suspend fun insert(todo: CheckboxData) = dao.insert(todo)

    suspend fun insert(todos: List<CheckboxData>) = dao.insert(todos)

    suspend fun update(todo: CheckboxData) = dao.update(todo)

    fun get(id: Long) = dao.get(id)

    fun getAll() = dao.getAll()

    fun getByNote(id: Long) = dao.getByNote(id)

}