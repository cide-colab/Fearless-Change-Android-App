package de.thkoeln.colab.fearlesschange.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.label.LabelDao
import de.thkoeln.colab.fearlesschange.persistance.note.Note
import de.thkoeln.colab.fearlesschange.persistance.note.NoteDao
import de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin.NoteLabelJoin
import de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin.NoteLabelJoinDao
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternDao
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInitializer
import de.thkoeln.colab.fearlesschange.persistance.statistic.Statistic
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticConverters
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticDao
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import de.thkoeln.colab.fearlesschange.persistance.todos.TodoDao

/**
 * Created by florianherborn on 30.07.18.
 */
@Database(
        entities = [
            Pattern::class,
            Statistic::class,
            Note::class,
            NoteLabelJoin::class,
            Label::class,
            Todo::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(StatisticConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun patternDao(): PatternDao
    abstract fun statisticDao(): StatisticDao
    abstract fun noteLabelJoinDao(): NoteLabelJoinDao
    abstract fun noteDao(): NoteDao
    abstract fun labelDao(): LabelDao
    abstract fun todoDao(): TodoDao

    companion object {
        private const val DB_NAME = "CardDatabase.db"

        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase =
                INSTANCE
                        ?: buildDatabase(context).also { INSTANCE = it }

        private fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .addCallback(PatternInitializer())
                .build()
    }
}