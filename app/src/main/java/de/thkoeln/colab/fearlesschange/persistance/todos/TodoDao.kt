package de.thkoeln.colab.fearlesschange.persistance.todos

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: CheckboxData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todos: List<CheckboxData>): List<Long>

    @Update
    suspend fun update(todos: CheckboxData)

    @Delete
    suspend fun delete(todos: CheckboxData)

    @Query("DELETE FROM todos")
    suspend fun deleteAll()

    @Query("SELECT * FROM todos WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<CheckboxData>

    @Query("SELECT * FROM todos")
    fun getAll(): LiveData<List<CheckboxData>>

    @Query("SELECT * FROM todos WHERE noteId = :id")
    fun getByNote(id: Long): LiveData<List<CheckboxData>>

    @Query("SELECT COUNT(*) FROM todos")
    fun getCount(): LiveData<Long>
}