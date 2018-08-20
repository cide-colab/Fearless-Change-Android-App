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

    @Query("SELECT * FROM card")
    fun getAll(): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    fun getById(id: Long): LiveData<Card>

    @Query("SELECT * FROM card ORDER BY RANDOM() LIMIT :count")
    fun getRandom(count: Int): LiveData<List<Card>>

    @Query("SELECT COUNT(*) FROM card")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM card LIMIT :index, 1")
    fun getElementWithIndex(index: Long): LiveData<Card>

    @Query("SELECT * FROM card WHERE favorite")
    fun getFavorites(): LiveData<List<Card>>

    @Query("SELECT c.* FROM card c, card_keyword ck WHERE (c.id = ck.cardId AND ck.keywordId) AND ck.keywordId IN (:keywordIds) GROUP BY c.id HAVING COUNT(*) >= :length")
    fun getCardsByKeywords(keywordIds: List<Long>, length: Int = keywordIds.size): LiveData<List<Card>>
}