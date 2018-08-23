package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface PatternDao {

    @Query("SELECT * FROM pattern")
    fun getAll(): LiveData<List<Pattern>>

    @Query("SELECT * FROM pattern WHERE id = :id LIMIT 1")
    fun get(id: Long): LiveData<Pattern>

    @Query("SELECT * FROM pattern WHERE id IN (:ids)")
    fun get(ids: List<Long>): LiveData<List<Pattern>>

    @Query("SELECT * FROM pattern p " +
            " LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId")
    fun getAllInfos(): LiveData<List<PatternInfo>>

    @Query("SELECT * FROM pattern p" +
            " LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId" +
            "  WHERE p.id = :id LIMIT 1")
    fun getInfo(id: Long): LiveData<PatternInfo>

    @Query("SELECT * FROM pattern p" +
            " LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId" +
            " WHERE p.id IN (:ids)")
    fun getInfos(ids: List<Long>): LiveData<List<PatternInfo>>

    @Query("SELECT COUNT(*) FROM pattern")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM pattern WHERE favorite")
    fun getFavorites(): LiveData<List<Pattern>>

    @Query("SELECT * FROM pattern p"  +
            " LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId" +
            " WHERE favorite")
    fun getFavoritesInfo(): LiveData<List<PatternInfo>>

    @Query("UPDATE pattern SET favorite = NOT favorite  WHERE id = :id")
    fun switchFavorite(id: Long)

    @Query("SELECT id FROM pattern")
    fun getAllIds(): LiveData<List<Long>>

}