package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.cardkeyword

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CardKeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardKeywords(cardKeyword: List<CardKeyword>)

    @Query("SELECT * FROM card_keyword WHERE cardId =:cardId")
    fun getKeywordsByCardId(cardId: Long): LiveData<List<CardKeyword>>
}