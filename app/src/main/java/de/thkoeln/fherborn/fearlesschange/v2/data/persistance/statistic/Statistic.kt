package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.statistic

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card.Card
import java.util.*

/**
 * Created by florianherborn on 09.08.18.
 */
@Entity(tableName = "card_statistic",
        indices = [Index("id")],
        foreignKeys = [(ForeignKey(
                entity = Card::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("cardId"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ))]
)
data class Statistic (
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,
        val cardId: Long,
        val action: StatisticAction,
        val timestamp: Date = Date()
)