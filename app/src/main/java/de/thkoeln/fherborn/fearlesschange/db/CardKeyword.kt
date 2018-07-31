package de.thkoeln.fherborn.fearlesschange.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(tableName = "card_keyword",
        primaryKeys = arrayOf("card_id", "keyword"),
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Card::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("card_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                ),
                ForeignKey(
                        entity = Keyword::class,
                        parentColumns = arrayOf("keyword"),
                        childColumns = arrayOf("keyword"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        )
)
data class CardKeyword (
    val card_id: Long,
    val keyword: String
)