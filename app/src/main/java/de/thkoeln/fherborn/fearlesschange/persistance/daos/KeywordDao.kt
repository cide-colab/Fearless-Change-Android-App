package de.thkoeln.fherborn.fearlesschange.persistance.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword
import io.reactivex.Flowable

@Dao
interface KeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeywords(keywords: List<Keyword>)

    @Query("SELECT * FROM keyword")
    fun getKeywords(): Flowable<List<Keyword>>
}