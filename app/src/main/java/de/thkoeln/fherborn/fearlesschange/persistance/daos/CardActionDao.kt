package de.thkoeln.fherborn.fearlesschange.persistance.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.thkoeln.fherborn.fearlesschange.persistance.models.Action
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardAction

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface CardActionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg cardAction: CardAction)

    @Query("SELECT * FROM card_action")
    fun getAll(): LiveData<List<CardAction>>

    @Query("SELECT * FROM card_action WHERE id = :id LIMIT 1")
    fun getById(id: Long): LiveData<CardAction>

    @Query("SELECT COUNT(*) FROM card_action")
    fun getCount(): LiveData<Long>

    @Query("SELECT COUNT(*) FROM card_action WHERE action = :action")
    fun getCountOfAction(action: Action): LiveData<Long>

    @Query("SELECT c.* FROM card_action c, (" +
            "  SELECT *, Count(action) actions " +
            "  FROM card_action " +
            "  WHERE action =:action " +
            "  GROUP BY cardId " +
            "  ORDER BY actions DESC " +
            "  LIMIT 1" +
            " ) AS r" +
            " WHERE r.id = c.id")
    fun getMostByAction(action: Action): LiveData<CardAction>

}