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
@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {
        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase = INSTANCE ?: synchronized(this) {
            buildDatabase(context).also { INSTANCE = it }
        }


        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, CardDatabase::class.java, "card_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            //Called when the database is created for the first time
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // insert the data on the IO Thread
                                Executors.newSingleThreadExecutor().execute {
                                    getInstance(context).cardDao().insertCards(CardData.CARDS)
                                }
                            }
                        })
                        .build()
    }
}