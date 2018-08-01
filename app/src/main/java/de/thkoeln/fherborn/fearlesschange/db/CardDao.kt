package de.thkoeln.fherborn.fearlesschange.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(card: List<Card>)

    @Query("SELECT * FROM card")
    fun getAll(): Flowable<List<Card>>

    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    fun getById(id: Int): Flowable<Card>

    @Query("SELECT * FROM card ORDER BY RANDOM() LIMIT :count")
    fun getRandom(count: Int): Flowable<List<Card>>
}