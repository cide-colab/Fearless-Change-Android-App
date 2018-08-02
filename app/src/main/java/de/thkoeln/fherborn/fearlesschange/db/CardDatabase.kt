package de.thkoeln.fherborn.fearlesschange.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.content.Context
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import de.thkoeln.fherborn.fearlesschange.db.initializer.CardInitializer
import de.thkoeln.fherborn.fearlesschange.db.initializer.CardKeywordInitializer
import de.thkoeln.fherborn.fearlesschange.db.initializer.KeywordInitializer
import java.util.concurrent.Executors

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
        @Volatile private var INSTANCE: CardDatabase? = null

        @Synchronized fun getInstance(context: Context): CardDatabase = INSTANCE?:buildDatabase(context).also { INSTANCE = it }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, CardDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(CardInitializer())
                .build()
    }
}

private class CardDatabaseCallback(val context: Context): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        Executors.newSingleThreadExecutor().execute({
//            CardInitializer(context).onInsert()
            KeywordInitializer(context).onInsert()
            CardKeywordInitializer(context).onInsert()
        })
    }

}