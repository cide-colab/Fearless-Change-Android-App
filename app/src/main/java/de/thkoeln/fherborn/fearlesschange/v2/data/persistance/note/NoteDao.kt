package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg notes: Note)

    @Update
    fun update(vararg notes: Note)

    @Delete
    fun delete(vararg notes: Note)

    @Query("SELECT * FROM note WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Note>


    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE patternId = :id")
    fun getNotesForPattern(id: Long): LiveData<List<Note>>

    @Query("SELECT COUNT(*) FROM note")
    fun getCount(): LiveData<Long>

}