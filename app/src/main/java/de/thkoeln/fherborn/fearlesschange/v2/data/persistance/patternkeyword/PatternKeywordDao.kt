package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.patternkeyword

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface PatternKeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardKeywords(patternKeyword: List<PatternKeyword>)

    @Query("SELECT * FROM pattern_keyword WHERE patternId =:patternId")
    fun getKeywordsByCardId(patternId: Long): LiveData<List<PatternKeyword>>
}