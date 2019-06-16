package de.thkoeln.colab.fearlesschange.data.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.thkoeln.colab.fearlesschange.data.persistance.keyword.Keyword
import de.thkoeln.colab.fearlesschange.data.persistance.keyword.KeywordDao
import de.thkoeln.colab.fearlesschange.data.persistance.keyword.KeywordInitializer
import de.thkoeln.colab.fearlesschange.data.persistance.note.Note
import de.thkoeln.colab.fearlesschange.data.persistance.note.NoteDao
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternDao
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInitializer
import de.thkoeln.colab.fearlesschange.data.persistance.patternkeyword.PatternKeyword
import de.thkoeln.colab.fearlesschange.data.persistance.patternkeyword.PatternKeywordDao
import de.thkoeln.colab.fearlesschange.data.persistance.patternkeyword.PatternKeywordInitializer
import de.thkoeln.colab.fearlesschange.data.persistance.statistic.Statistic
import de.thkoeln.colab.fearlesschange.data.persistance.statistic.StatisticConverters
import de.thkoeln.colab.fearlesschange.data.persistance.statistic.StatisticDao

/**
 * Created by florianherborn on 30.07.18.
 */
@Database(
        entities = [Pattern::class, Keyword::class, PatternKeyword::class, Statistic::class, Note::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(StatisticConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun patternDao(): PatternDao
    abstract fun statisticDao(): StatisticDao
    abstract fun keywordDao(): KeywordDao
    abstract fun patternKeywordDao(): PatternKeywordDao
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DB_NAME = "CardDatabase.db"

        private var INSTANCE: AppDatabase? = null

        @Synchronized fun getInstance(context: Context): AppDatabase =
                INSTANCE?:buildDatabase(context).also { INSTANCE = it }

        private fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .addCallback(PatternInitializer())
                .addCallback(KeywordInitializer())
                .addCallback(PatternKeywordInitializer())
                .build()
    }
}