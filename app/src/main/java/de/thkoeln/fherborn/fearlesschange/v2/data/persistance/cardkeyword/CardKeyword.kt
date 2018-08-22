package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.cardkeyword

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.Card
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.keyword.Keyword

@Entity(tableName = "card_keyword",
        primaryKeys = ["cardId", "keywordId"],
        indices = [Index("cardId"), Index("keywordId")],
        foreignKeys = [(ForeignKey(
                entity = Card::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("cardId"),
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
data class CardKeyword(
        val cardId: Long,
        val keywordId: Long
)