package de.thkoeln.fherborn.fearlesschange.persistance.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg card: Card)

    @Update
    fun update(vararg card: Card)

    @Query("SELECT * FROM cardId")
    fun getAll(): LiveData<List<Card>>

    @Query("SELECT * FROM cardId WHERE id = :id LIMIT 1")
    fun getById(id: Long): LiveData<Card>

    @Query("SELECT * FROM cardId ORDER BY RANDOM() LIMIT :count")
    fun getRandom(count: Int): LiveData<List<Card>>

    @Query("SELECT COUNT(*) FROM cardId")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM cardId LIMIT :index, 1")
    fun getElementWithIndex(index: Long): LiveData<Card>

    @Query("SELECT * FROM cardId WHERE favorite")
    fun getFavorites(): LiveData<List<Card>>

}