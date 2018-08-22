package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface StatisticDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg cardStatisticAction: Statistic)

    @Query("SELECT * FROM card_statistic")
    fun getAll(): LiveData<List<Statistic>>

    @Query("SELECT * FROM card_statistic WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Statistic>

    @Query("SELECT COUNT(*) FROM card_statistic")
    fun getCount(): LiveData<Long>

    @Query("SELECT COUNT(*) FROM card_statistic WHERE `action` = :action")
    fun getActionCount(action: StatisticAction): LiveData<Long>

    @Query("SELECT c.* FROM card_statistic c, (" +
            "  SELECT *, Count(action) actions " +
            "  FROM card_statistic " +
            "  WHERE action =:action " +
            "  GROUP BY cardId " +
            "  ORDER BY actions DESC " +
            "  LIMIT 1" +
            " ) AS r" +
            " WHERE r.id = c.id")
    fun getMostCommonByAction(action: StatisticAction): LiveData<Statistic>

}