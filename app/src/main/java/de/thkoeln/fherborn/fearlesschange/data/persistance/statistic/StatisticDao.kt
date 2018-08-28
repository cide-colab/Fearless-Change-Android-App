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
    fun insert(vararg actions: Statistic)

    @Query("SELECT * FROM statistic")
    fun getAll(): LiveData<List<Statistic>>

    @Query("SELECT * FROM statistic WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Statistic>

    @Query("SELECT COUNT(*) FROM statistic")
    fun getCount(): LiveData<Long>

    @Query("SELECT COUNT(*) FROM statistic WHERE `action` = :action")
    fun getActionCount(action: StatisticAction): LiveData<Long>

    @Query("SELECT c.* FROM statistic c, " +
            "(" +
            "  SELECT *, Count(action) actions " +
            "  FROM statistic " +
            "  WHERE action =:action " +
            "  GROUP BY patternId " +
            "  ORDER BY actions DESC " +
            "  LIMIT 1" +
            " ) AS r" +
            " WHERE r.id = c.id")
    fun getMostCommonByAction(action: StatisticAction): LiveData<Statistic>

}