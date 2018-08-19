package de.thkoeln.fherborn.fearlesschange.persistance.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword

@Dao
interface KeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeywords(keywords: List<Keyword>)

    @Query("SELECT * FROM keyword")
    fun getKeywords(): LiveData<List<Keyword>>

    @Query("SELECT * FROM keyword WHERE keyword.keyword = :keyword LIMIT 1")
    fun getKeywordByKeyword(keyword: String): LiveData<Keyword>
}