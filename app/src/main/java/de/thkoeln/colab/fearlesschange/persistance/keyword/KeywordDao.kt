package de.thkoeln.colab.fearlesschange.persistance.keyword

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeywords(keywords: List<Keyword>)

    @Query("SELECT * FROM keyword")
    fun getKeywords(): LiveData<List<Keyword>>

    @Query("SELECT * FROM keyword WHERE keyword.keyword = :keyword LIMIT 1")
    fun getKeywordByKeyword(keyword: String): LiveData<Keyword>
}