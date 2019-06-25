package de.thkoeln.colab.fearlesschange.persistance.patternkeyword

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PatternKeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardKeywords(patternKeyword: List<PatternKeyword>)

    @Query("SELECT * FROM pattern_keyword WHERE patternId =:patternId")
    fun getKeywordsByCardId(patternId: Long): LiveData<List<PatternKeyword>>
}