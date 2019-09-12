package de.thkoeln.colab.fearlesschange.persistance.label

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(label: Label): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(labels: List<Label>): List<Long>

    @Update
    suspend fun update(vararg labels: Label)

    @Delete
    suspend fun delete(vararg labels: Label)

    @Query("DELETE FROM labels")
    suspend fun deleteAll()

    @Query("SELECT * FROM labels WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Label>

    @Query("SELECT * FROM labels")
    suspend fun getAll(): List<Label>

    @Query("SELECT COUNT(*) FROM labels")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM labels WHERE TRIM(LOWER(name)) = TRIM(LOWER(:name)) LIMIT 1")
    suspend fun getByName(name: String): Label?

    @Query("SELECT * FROM labels WHERE name LIKE '%'||:name||'%'")
    suspend fun getLike(name: String): List<Label>

}