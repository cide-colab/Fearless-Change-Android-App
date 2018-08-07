package de.thkoeln.fherborn.fearlesschange.persistance.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(tableName = "card_keyword",
        primaryKeys = ["card_id", "keyword_id"],
        foreignKeys = [(ForeignKey(
                entity = Card::class,
                parentColumns = ["id"],
                childColumns = ["card_id"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )), (ForeignKey(
                entity = Keyword::class,
                parentColumns = ["id"],
                childColumns = ["keyword_id"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ))]
)
data class CardKeyword(
        val card_id: Long,
        val keyword_id: Long
)