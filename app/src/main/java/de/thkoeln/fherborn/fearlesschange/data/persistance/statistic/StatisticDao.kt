package de.thkoeln.fherborn.fearlesschange.data.persistance.statistic

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternInfo

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

    @Query("SELECT p.*, n.noteCount FROM (SELECT patternId, COUNT(`action`) as actions FROM statistic WHERE `action`=:action GROUP BY patternId ORDER BY actions DESC LIMIT 1) s LEFT JOIN pattern p ON s.patternId = p.id LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON s.patternId = n.patternId")
    fun getMostCommonByAction(action: StatisticAction): LiveData<PatternInfo>

    @Query("DELETE FROM statistic WHERE `action` = :action")
    fun deleteByAction(action: StatisticAction)

}