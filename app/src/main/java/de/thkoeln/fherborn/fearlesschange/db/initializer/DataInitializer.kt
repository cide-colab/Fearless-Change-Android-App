package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Florian on 02.08.2018.
 */
abstract class DataInitializer<T>(private val dbName: String): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL(insertAllQuery(getItems()))
    }

    private fun insertAllQuery(items: List<T>) =
            items.joinToString(", ", prefix = "INSERT INTO $dbName VALUES ") { getValueString(it) }

    private fun getValueString(item: T) = getItemValues(item).joinToString(separator = ", ", prefix = "(", postfix = ")") {
        when (it){
            is String -> """"$it""""
            else -> it.toString()
        }
    }

    abstract fun getItemValues(item: T): List<Any>
    abstract fun getItems(): List<T>
}