package de.thkoeln.colab.fearlesschange.persistance

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by Florian on 02.08.2018.
 */
abstract class DataInitializer<T>(private val dbName: String): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        insertAllQuery(getItems())?.let { db.execSQL(it) }
    }

    private fun insertAllQuery(items: List<T>): String? {
        if(items.isEmpty()) return null
        return items.joinToString(", ", prefix = "INSERT INTO $dbName ${getColumnString()} VALUES ") { getValueString(it) }
    }


    private fun getValueString(item: T) = getItemValues(item).values.joinToString(separator = ", ", prefix = "(", postfix = ")") {
        when (it){
            is Boolean -> if(it) "1" else "0"
            is String -> """"$it""""
            else -> it.toString()
        }
    }

    private fun getColumnString() = getItemValues().keys.joinToString(separator = ", ", prefix = "(", postfix = ")")
    abstract fun getItemValues(item: T? = null): HashMap<String, Any?>
    abstract fun getItems(): List<T>
}