package de.thkoeln.colab.fearlesschange.persistance.todos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import de.thkoeln.colab.fearlesschange.persistance.note.Note


@Entity(tableName = "todos",
        indices = [Index("id", "noteId")],
        foreignKeys = [
            ForeignKey(
                    entity = Note::class,
                    parentColumns = ["id"],
                    childColumns = ["noteId"],
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ]
)

data class Todo(
        var state: Boolean,
        var text: String,
        val noteId: Long,
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0
)