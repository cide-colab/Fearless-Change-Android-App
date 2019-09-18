package de.thkoeln.colab.fearlesschange.persistance.pattern

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
