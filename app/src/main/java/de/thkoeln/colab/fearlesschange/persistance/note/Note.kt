package de.thkoeln.colab.fearlesschange.persistance.note

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern

/**
 * Created by florianherborn on 13.08.18.
 */
@Entity(tableName = "noteData",
        indices = [Index("patternId"), Index("id")],
        foreignKeys = [ForeignKey(
                entity = Pattern::class,
                parentColumns = ["id"],
                childColumns = ["patternId"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )]
)
data class Note(
        var text: String,
        val patternId: Long,
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0
)