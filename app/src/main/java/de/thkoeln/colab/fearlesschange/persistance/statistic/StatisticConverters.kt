package de.thkoeln.colab.fearlesschange.persistance.statistic

import androidx.room.TypeConverter
import java.util.*

class StatisticConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStringToAction(value: String?): StatisticAction? {
        return value?.let { StatisticAction.valueOf(it) }
    }

    @TypeConverter
    fun actionToString(date: StatisticAction?): String? {
        return date?.name
    }
}