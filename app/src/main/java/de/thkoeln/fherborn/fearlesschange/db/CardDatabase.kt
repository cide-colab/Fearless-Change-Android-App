package de.thkoeln.fherborn.fearlesschange.db

import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import java.util.concurrent.Executors

/**
 * Created by florianherborn on 30.07.18.
 */
@Database(entities = [
    Card::class,
    Keyword::class,
    CardKeyword::class],
        version = 2,
        exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao
    abstract fun keywordDao(): KeywordDao
    abstract fun cardKeywordDao(): CardKeywordDao

    companion object {
        private var INSTANCE: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase? {
            if (INSTANCE == null) {
                synchronized(CardDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CardDatabase::class.java, "CardDatabase.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)

                                    Executors.newSingleThreadExecutor().execute({
                                        getInstance(context)?.cardDao()?.insertAll(CardData.CARDS)
                                        getInstance(context)?.keywordDao()?.insertKeywords(KeywordData.KEYWORDS)
                                        getInstance(context)?.cardKeywordDao()?.insertCardKeywords(CardKeywordData.CARDKEYWORDS)
                                    })

                                }
                            })
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}