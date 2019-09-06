package de.thkoeln.colab.fearlesschange.persistance.todos

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todos: List<Todo>): List<Long>

    @Update
    suspend fun update(todos: Todo): Int

    @Delete
    suspend fun delete(todos: Todo)

    @Query("DELETE FROM todos")
    suspend fun deleteAll()

    @Query("SELECT * FROM todos WHERE id = :id LIMIT 1")
    suspend fun get(id: Long): Todo

    @Query("SELECT * FROM todos")
    fun getAll(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE noteId = :id")
    suspend fun getByNote(id: Long): List<Todo>

    @Query("SELECT COUNT(*) FROM todos")
    fun getCount(): LiveData<Long>
}