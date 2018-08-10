package de.thkoeln.fherborn.fearlesschange.persistance.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatisticAction
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatistic

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface CardStatisticDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg cardStatisticAction: CardStatistic)

    @Query("SELECT * FROM card_statistic")
    fun getAll(): LiveData<List<CardStatistic>>

    @Query("SELECT * FROM card_statistic WHERE id = :id LIMIT 1")
    fun getById(id: Long): LiveData<CardStatistic>

    @Query("SELECT COUNT(*) FROM card_statistic")
    fun getCount(): LiveData<Long>

    @Query("SELECT COUNT(*) FROM card_statistic WHERE `action` = :action")
    fun getCountOfAction(action: CardStatisticAction): LiveData<Long>

    @Query("SELECT c.* FROM card_statistic c, (" +
            "  SELECT *, Count(action) actions " +
            "  FROM card_statistic " +
            "  WHERE action =:action " +
            "  GROUP BY cardId " +
            "  ORDER BY actions DESC " +
            "  LIMIT 1" +
            " ) AS r" +
            " WHERE r.id = c.id")
    fun getMostByAction(action: CardStatisticAction): LiveData<CardStatistic>

}