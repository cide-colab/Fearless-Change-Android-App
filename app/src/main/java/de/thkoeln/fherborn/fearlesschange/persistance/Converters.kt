package de.thkoeln.fherborn.fearlesschange.persistance

import android.arch.persistence.room.TypeConverter
import de.thkoeln.fherborn.fearlesschange.persistance.models.Action

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
    fun fromStringToAction(value: String?): Action? {
        return value?.let { Action.valueOf(it) }
    }

    @TypeConverter
    fun actionToString(date: Action?): String? {
        return date?.name
    }
}