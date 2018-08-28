package de.thkoeln.fherborn.fearlesschange.data.persistance.pattern

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by florianherborn on 30.07.18.
 */
@Entity(tableName = "pattern",
        indices = [(Index("id"))])
data class Pattern (
        @PrimaryKey(autoGenerate = false)
        val id: Long,
        val title: String,
        val pictureName: String,
        val problem: String,
        val summary: String,
        val solution: String,
        var favorite: Boolean = false
)