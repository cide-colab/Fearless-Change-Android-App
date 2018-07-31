package de.thkoeln.fherborn.fearlesschange.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface CardKeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardKeywords(cardKeyword: List<CardKeyword>)

    @Query("SELECT keyword FROM card_keyword as ck WHERE ck.card_id =:cardId")
    fun getKeywordsByCardId(cardId: Long): Flowable<List<String>>
}