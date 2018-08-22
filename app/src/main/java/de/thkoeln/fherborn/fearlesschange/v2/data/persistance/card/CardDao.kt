package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardWithNoteCount

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    fun getAll(): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Card>

    @Query("SELECT * FROM card WHERE id IN (:ids)")
    fun get(ids: List<Long>): LiveData<List<Card>>

    @Query("SELECT * FROM card c " +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId")
    fun getAllInfos(): LiveData<List<CardWithNoteCount>>

    @Query("SELECT * FROM card c" +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId" +
            "  WHERE c.id = :id LIMIT 1")
    fun getInfo(id: Long): LiveData<CardInfo>

    @Query("SELECT * FROM card c" +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId" +
            " WHERE c.id IN (:ids)")
    fun getInfos(ids: List<Long>): LiveData<List<CardInfo>>

    @Query("SELECT COUNT(*) FROM card")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM card WHERE favorite")
    fun getFavorites(): LiveData<List<Card>>

    @Query("SELECT * FROM card c"  +
            " LEFT JOIN (SELECT COUNT(cardId) as noteCount, cardId FROM note GROUP BY cardId) n ON c.id = n.cardId" +
            " WHERE favorite")
    fun getFavoritesInfo(): LiveData<List<CardWithNoteCount>>

    @Query("UPDATE card SET favorite = NOT favorite  WHERE id = :id")
    fun switchFavorite(id: Long)

    @Query("SELECT id FROM card")
    fun getAllIds(): LiveData<List<Long>>

}