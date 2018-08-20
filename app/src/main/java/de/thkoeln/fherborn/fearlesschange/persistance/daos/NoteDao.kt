package de.thkoeln.fherborn.fearlesschange.persistance.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import de.thkoeln.fherborn.fearlesschange.persistance.models.Note

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

    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE cardId = :id")
    fun getByCardId(id: Long): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id LIMIT 1")
    fun getById(id: Long): LiveData<Note>

    @Query("SELECT COUNT(*) FROM note")
    fun getCount(): LiveData<Long>

    @Query("SELECT COUNT(*) FROM note WHERE cardId = :id")
    fun getCountByCardId(id: Long): LiveData<Long>

}