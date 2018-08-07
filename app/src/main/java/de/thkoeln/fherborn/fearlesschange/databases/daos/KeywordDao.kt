package de.thkoeln.fherborn.fearlesschange.databases.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.thkoeln.fherborn.fearlesschange.databases.models.Keyword

@Dao
interface KeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeywords(keywords: List<Keyword>)

    @Query("SELECT * FROM keyword")
    fun getKeywords(): LiveData<List<Keyword>>
}