package de.thkoeln.colab.fearlesschange.persistance.todos

import android.content.Context
import de.thkoeln.colab.fearlesschange.persistance.AppDatabase

class TodoRepository(context: Context?) {

    private val dao = AppDatabase.getInstance(context
            ?: throw RuntimeException("Application is null")).todoDao()

    suspend fun insert(todo: Todo) = dao.insert(todo)

    suspend fun insert(todos: List<Todo>) = dao.insert(todos)

    suspend fun update(todo: Todo) = dao.update(todo)

    suspend fun get(id: Long) = dao.get(id)

    fun getAll() = dao.getAll()

    suspend fun getByNote(id: Long) = dao.getByNote(id)

}