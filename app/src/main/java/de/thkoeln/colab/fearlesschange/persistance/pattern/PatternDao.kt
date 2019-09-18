package de.thkoeln.colab.fearlesschange.persistance.pattern

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * Created by florianherborn on 30.07.18.
 */
@Dao
interface PatternDao {

    @Query("SELECT * FROM pattern")
    fun getAll(): LiveData<List<Pattern>>

    @Query("""
        SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount 
        FROM (SELECT * FROM pattern p LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM noteData GROUP BY patternId) n ON p.id = n.patternId 
        WHERE p.id = :id LIMIT 1)
        """)
    suspend fun get(id: Long): PatternPreviewData

    @Query("SELECT * FROM pattern WHERE id IN (:ids)")
    fun get(ids: List<Long>): LiveData<List<Pattern>>

    @Query("""
        SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (
        SELECT * FROM pattern p 
        LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM noteData GROUP BY patternId) n ON p.id = n.patternId)
    """)
    fun getAllInfo(): LiveData<List<PatternPreviewData>>

    @Query("""
        SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount 
        FROM (SELECT * FROM pattern p LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM noteData GROUP BY patternId) n ON p.id = n.patternId 
        WHERE p.id = :id LIMIT 1)
        """)
    fun getInfo(id: Long): LiveData<PatternPreviewData>

    @Query("""
        SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (
        SELECT * FROM pattern p
        LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM noteData GROUP BY patternId) n ON p.id = n.patternId
        WHERE p.id IN (:ids)
        )
        """)
    fun getInfos(ids: List<Long>): LiveData<List<PatternPreviewData>>

    @Query("SELECT COUNT(*) FROM pattern")
    fun getCount(): LiveData<Long>

    @Query("SELECT * FROM pattern WHERE favorite")
    fun getFavorites(): LiveData<List<Pattern>>

    @Query("""
        SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (
        SELECT * FROM pattern p
        LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM noteData GROUP BY patternId) n ON p.id = n.patternId
        WHERE favorite
        )
        """)
    fun getFavoritesInfo(): LiveData<List<PatternPreviewData>>

    @Query("UPDATE pattern SET favorite = NOT favorite  WHERE id = :id")
    suspend fun switchFavorite(id: Long)

    @Query("SELECT id FROM pattern")
    fun getAllIds(): LiveData<List<Long>>

    @Query("""
        SELECT id, title, pictureName, problem, summary, solution, favorite, noteCount FROM (
        SELECT * FROM pattern p
        LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM noteData GROUP BY patternId) n ON p.id = n.patternId
        ORDER BY RANDOM() LIMIT :count
        )
        """)
    fun getRandom(count: Int): LiveData<List<PatternPreviewData>>

    @Query("UPDATE pattern SET favorite = :flag")
    suspend fun setAllFavorites(flag: Boolean)

    @Query("SELECT p.id, p.title, p.pictureName, p.problem, p.summary, p.solution, p.favorite, n.noteCount FROM pattern p LEFT JOIN (SELECT COUNT(patternId) as noteCount, patternId FROM noteData GROUP BY patternId) n ON p.id = n.patternId WHERE problem LIKE '%'||:query||'%' OR solution LIKE '%'||:query||'%' OR summary LIKE '%'||:query||'%' OR title LIKE '%'||:query||'%'")
    suspend fun getLike(query: String): List<PatternPreviewData>
}