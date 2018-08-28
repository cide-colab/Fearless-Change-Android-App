package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.patternkeyword

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern.Pattern
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword.Keyword

@Entity(tableName = "pattern_keyword",
        primaryKeys = ["patternId", "keywordId"],
        indices = [Index("patternId"), Index("keywordId")],
        foreignKeys = [(ForeignKey(
                entity = Pattern::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("patternId"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )), (ForeignKey(
                entity = Keyword::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("keywordId"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ))]
)
data class PatternKeyword(
        val patternId: Long,
        val keywordId: Long
)