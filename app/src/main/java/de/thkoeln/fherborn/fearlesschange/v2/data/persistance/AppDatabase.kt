package de.thkoeln.fherborn.fearlesschange.v2.data.persistance

import android.content.Context
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.cardkeyword.CardKeywordDao
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword.KeywordDao
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.Card
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.CardDao
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.CardInitializer
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.cardkeyword.CardKeyword
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.cardkeyword.CardKeywordInitializer
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword.Keyword
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword.KeywordInitializer
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.Note
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.note.NoteDao
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.Statistic
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.StatisticConverters
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic.StatisticDao

/**
 * Created by florianherborn on 30.07.18.
 */
@Database(
        entities = [Card::class, Keyword::class, CardKeyword::class, Statistic::class, Note::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(StatisticConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao
    abstract fun statisticDao(): StatisticDao
    abstract fun keywordDao(): KeywordDao
    abstract fun cardKeywordDao(): CardKeywordDao
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DB_NAME = "CardDatabase.db"
        private var INSTANCE: AppDatabase? = null

        @Synchronized fun getInstance(context: Context): AppDatabase =
                INSTANCE?:buildDatabase(context).also { INSTANCE = it }

        private fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .addCallback(CardInitializer())
                .addCallback(KeywordInitializer())
                .addCallback(CardKeywordInitializer())
                .build()
    }
}