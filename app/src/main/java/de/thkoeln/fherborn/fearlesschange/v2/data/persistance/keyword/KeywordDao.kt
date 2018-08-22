package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface KeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeywords(keywords: List<Keyword>)

    @Query("SELECT * FROM keyword")
    fun getKeywords(): LiveData<List<Keyword>>
}