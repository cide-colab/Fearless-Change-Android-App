package de.thkoeln.fherborn.fearlesschange.persistance.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardKeyword
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword
import io.reactivex.Flowable

@Dao
interface CardKeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardKeywords(cardKeyword: List<CardKeyword>)

    @Query("SELECT * FROM card_keyword WHERE card_id =:cardId")
    fun getKeywordsByCardId(cardId: Long): LiveData<List<CardKeyword>>
}