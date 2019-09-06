package de.thkoeln.colab.fearlesschange.persistance.noteLabelJoin

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.note.Note

@Entity(tableName = "note_label_join",
        primaryKeys = ["noteId", "labelId"],
        indices = [Index("noteId"), Index("labelId")],
        foreignKeys = [
            ForeignKey(
                    entity = Note::class,
                    parentColumns = ["id"],
                    childColumns = ["noteId"],
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            ),
            ForeignKey(
                    entity = Label::class,
                    parentColumns = ["id"],
                    childColumns = ["labelId"],
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ]
)

data class NoteLabelJoin(
        val noteId: Long,
        val labelId: Long
)
