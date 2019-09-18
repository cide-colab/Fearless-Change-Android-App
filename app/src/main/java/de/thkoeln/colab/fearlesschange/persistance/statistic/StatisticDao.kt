package de.thkoeln.colab.fearlesschange.persistance.statistic

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface StatisticDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg actions: Statistic)

    @Query("SELECT * FROM statistic")
    fun getAll(): LiveData<List<Statistic>>

    @Query("SELECT * FROM statistic WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Statistic>

    @Query("SELECT COUNT(*) FROM statistic")
    fun getCount(): LiveData<Long>

    @Query("SELECT COUNT(*) FROM statistic WHERE `action` = :action")
    fun getActionCount(action: StatisticAction): LiveData<Long>

    @Query("SELECT p.*, n.noteCount FROM (SELECT patternId, COUNT(`action`) as actions FROM statistic WHERE `action`=:action GROUP BY patternId ORDER BY actions DESC LIMIT 1) s LEFT JOIN pattern p ON s.patternId = p.id LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM noteData GROUP BY patternId) n ON s.patternId = n.patternId")
    fun getMostCommonByAction(action: StatisticAction): LiveData<PatternPreviewData?>

    @Query("DELETE FROM statistic WHERE `action` = :action")
    suspend fun deleteByAction(action: StatisticAction)

}