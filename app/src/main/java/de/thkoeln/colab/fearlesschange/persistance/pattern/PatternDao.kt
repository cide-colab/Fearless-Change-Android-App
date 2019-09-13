package de.thkoeln.colab.fearlesschange.persistance.pattern

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface PatternDao {

    @Query("SELECT * FROM patternInfo")
    fun getAll(): LiveData<List<Pattern>>

    @Query("""
        SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount 
        FROM (SELECT * FROM patternInfo p LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId 
        WHERE p.id = :id LIMIT 1)
        """)
    suspend fun get(id: Long): PatternInfo

    @Query("SELECT * FROM patternInfo WHERE id IN (:ids)")
    fun get(ids: List<Long>): LiveData<List<Pattern>>

    @Query("SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (" +
            "SELECT * FROM patternInfo p " +
            " LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId" +
            ")")
    fun getAllInfo(): LiveData<List<PatternInfo>>

    @Query("""
        SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount 
        FROM (SELECT * FROM patternInfo p LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId 
        WHERE p.id = :id LIMIT 1)
        """)
    fun getInfo(id: Long): LiveData<PatternInfo>

    @Query("SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (" +
            "SELECT * FROM patternInfo p" +
            " LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId" +
            " WHERE p.id IN (:ids)" +
            ")")
    fun getInfos(ids: List<Long>): LiveData<List<PatternInfo>>

    @Query("SELECT COUNT(*) FROM patternInfo")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM patternInfo WHERE favorite")
    fun getFavorites(): LiveData<List<Pattern>>

    @Query("SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (" +
            "SELECT * FROM patternInfo p" +
            " LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId" +
            " WHERE favorite" +
            ")")
    fun getFavoritesInfo(): LiveData<List<PatternInfo>>

    @Query("UPDATE patternInfo SET favorite = NOT favorite  WHERE id = :id")
    fun switchFavorite(id: Long)

    @Query("SELECT id FROM patternInfo")
    fun getAllIds(): LiveData<List<Long>>

    @Query("SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (" +
            "SELECT * FROM patternInfo p" +
            " LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId" +
            " ORDER BY RANDOM() LIMIT :count" +
            ")")
    fun getRandom(count: Int): LiveData<List<PatternInfo>>

    @Query("UPDATE patternInfo SET favorite = :flag")
    fun setAllFavorites(flag: Boolean)

    @Query("SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (SELECT * FROM (SELECT c.* FROM patternInfo c, pattern_keyword ck WHERE c.id = ck.patternId AND ck.keywordId IN (:keywordIds) GROUP BY c.id HAVING COUNT(*) >= :length) cards LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON cards.id = n.patternId)")
    fun getByKeywordIds(keywordIds: List<Long>, length: Int = keywordIds.size): LiveData<List<PatternInfo>>


    @Query("SELECT p.id, p.title, p.pictureName, p.problem, p.summary, p.solution, p.favorite, n.noteCount FROM patternInfo p LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM note GROUP BY patternId) n ON p.id = n.patternId WHERE problem LIKE '%'||:query||'%' OR solution LIKE '%'||:query||'%' OR summary LIKE '%'||:query||'%' OR title LIKE '%'||:query||'%'")
    suspend fun getLike(query: String): List<PatternInfo>
}