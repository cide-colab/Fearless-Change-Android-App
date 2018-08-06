package de.thkoeln.fherborn.fearlesschange.databases.initializer

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
            items.joinToString(", ", prefix = "INSERT INTO $dbName ${getColumnString()} VALUES ") { getValueString(it) }

    private fun getValueString(item: T) = getItemValues(item).values.joinToString(separator = ", ", prefix = "(", postfix = ")") {
        when (it){
            is String -> """"$it""""
            else -> it.toString()
        }
    }

    private fun getColumnString() = getItemValues().keys.joinToString(separator = ", ", prefix = "(", postfix = ")")
    abstract fun getItemValues(item: T? = null): HashMap<String, Any?>
    abstract fun getItems(): List<T>
}