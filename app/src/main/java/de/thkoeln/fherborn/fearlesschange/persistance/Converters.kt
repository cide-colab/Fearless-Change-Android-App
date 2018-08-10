package de.thkoeln.fherborn.fearlesschange.persistance

import android.arch.persistence.room.TypeConverter
import de.thkoeln.fherborn.fearlesschange.persistance.models.CardStatisticAction

import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStringToAction(value: String?): CardStatisticAction? {
        return value?.let { CardStatisticAction.valueOf(it) }
    }

    @TypeConverter
    fun actionToString(date: CardStatisticAction?): String? {
        return date?.name
    }
}