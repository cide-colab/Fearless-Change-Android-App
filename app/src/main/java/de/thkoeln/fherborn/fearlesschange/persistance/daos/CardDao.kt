package de.thkoeln.fherborn.fearlesschange.persistance.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardWithNoteCount

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

    @Query("SELECT * FROM card c " +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId")
    fun getAllWithNoteCount(): LiveData<List<CardWithNoteCount>>

    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    fun getById(id: Long): LiveData<Card>

    @Query("SELECT * FROM card c " +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId" +
            " WHERE id = :id LIMIT 1")
    fun getByIdWithNoteCount(id: Long): LiveData<CardWithNoteCount>

    @Query("SELECT * FROM card ORDER BY RANDOM() LIMIT :count")
    fun getRandom(count: Int): LiveData<List<Card>>

    @Query("SELECT * FROM card c " +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId" +
            " ORDER BY RANDOM() LIMIT :count")
    fun getRandomWithNoteCount(count: Int): LiveData<List<CardWithNoteCount>>

    @Query("SELECT COUNT(*) FROM card")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM card c" +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId" +
            " LIMIT :index, 1")
    fun getElementWithIndexWithNoteCount(index: Long): LiveData<CardWithNoteCount>

    @Query("SELECT * FROM card LIMIT :index, 1")
    fun getElementWithIndex(index: Long): LiveData<Card>

    @Query("SELECT * FROM card WHERE favorite")
    fun getFavorites(): LiveData<List<Card>>

    @Query("SELECT * FROM card c "  +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId" +
            " WHERE favorite")
    fun getFavoritesWithNoteCount(): LiveData<List<CardWithNoteCount>>

    @Query("UPDATE card SET favorite = NOT favorite  WHERE id = :id")
    fun switchFavorite(id: Long)

}