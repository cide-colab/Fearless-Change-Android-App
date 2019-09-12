package de.thkoeln.colab.fearlesschange.persistance.patternkeyword

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import de.thkoeln.colab.fearlesschange.persistance.keyword.Keyword
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern

@Entity(tableName = "pattern_keyword",
        primaryKeys = ["patternId", "keywordId"],
        indices = [Index("patternId"), Index("keywordId")],
        foreignKeys = [
            ForeignKey(
                    entity = Pattern::class,
                    parentColumns = ["id"],
                    childColumns = ["patternId"],
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            ),
            ForeignKey(
                    entity = Keyword::class,
                    parentColumns = ["id"],
                    childColumns = ["keywordId"],
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ]
)
data class PatternKeyword(
        val patternId: Long,
        val keywordId: Long
)