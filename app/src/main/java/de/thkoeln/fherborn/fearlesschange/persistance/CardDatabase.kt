package de.thkoeln.fherborn.fearlesschange.persistance

import android.content.Context
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import de.thkoeln.fherborn.fearlesschange.persistance.daos.CardKeywordDao
import de.thkoeln.fherborn.fearlesschange.persistance.daos.KeywordDao
import de.thkoeln.fherborn.fearlesschange.persistance.daos.CardDao
import de.thkoeln.fherborn.fearlesschange.persistance.initializer.CardInitializer
import de.thkoeln.fherborn.fearlesschange.persistance.initializer.CardKeywordInitializer
import de.thkoeln.fherborn.fearlesschange.persistance.initializer.KeywordInitializer
import de.thkoeln.fherborn.fearlesschange.persistance.models.Card
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardKeyword
import de.thkoeln.fherborn.fearlesschange.persistance.models.Keyword

/**
 * Created by florianherborn on 30.07.18.
 */
@Database(
        entities = [Card::class, Keyword::class, CardKeyword::class],
        version = 1,
        exportSchema = false
)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao
    abstract fun keywordDao(): KeywordDao
    abstract fun cardKeywordDao(): CardKeywordDao

    companion object {
        private const val DB_NAME = "CardDatabase.db"
        private var INSTANCE: CardDatabase? = null

        @Synchronized fun getInstance(context: Context): CardDatabase = INSTANCE?:buildDatabase(context).also { INSTANCE = it }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, CardDatabase::class.java, DB_NAME)
                .addCallback(CardInitializer())
                .addCallback(KeywordInitializer())
                .addCallback(CardKeywordInitializer())
                .build()
    }
}