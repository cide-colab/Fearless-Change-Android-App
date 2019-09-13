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

    @Query("DELETE FROM noteData")
    suspend fun deleteAll()

    @Query("SELECT * FROM noteData WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Note>

    @Query("SELECT * FROM noteData")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM noteData WHERE patternId = :id")
    suspend fun getNotesForPattern(id: Long): List<Note>

    @Query("SELECT COUNT(*) FROM noteData")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM noteData WHERE text LIKE '%'||:query||'%'")
    suspend fun getLike(query: String): List<Note>
}