package de.thkoeln.fherborn.fearlesschange.data.persistance.note

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.Pattern

/**
 * Created by florianherborn on 13.08.18.
 */
@Entity(tableName = "note",
        indices = [Index("patternId")],
        foreignKeys = [(ForeignKey(
                entity = Pattern::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("patternId"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ))]
)
data class Note(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        var title: String,
        var text: String,
        val patternId: Long
)