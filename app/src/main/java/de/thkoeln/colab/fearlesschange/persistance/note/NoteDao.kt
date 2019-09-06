package de.thkoeln.colab.fearlesschange.persistance.note

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notes: Note): Long

    @Update
    suspend fun update(notes: Note)

    @Delete
    suspend fun delete(notes: Note)

    @Query("DELETE FROM note")
    suspend fun deleteAll()

    @Query("SELECT * FROM note WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Note>

    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE patternId = :id")
    suspend fun getNotesForPattern(id: Long): List<Note>

    @Query("SELECT COUNT(*) FROM note")
    fun getCount(): LiveData<Long>

}