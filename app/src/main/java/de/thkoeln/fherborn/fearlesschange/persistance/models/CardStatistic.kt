package de.thkoeln.fherborn.fearlesschange.persistance.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by florianherborn on 09.08.18.
 */
@Entity(tableName = "card_statistic",
        foreignKeys = [(ForeignKey(
                entity = Card::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("cardId"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ))]
)
data class CardStatistic (
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,
        val cardId: Long,
        val action: CardStatisticAction,
        val timestamp: Date = Date()
)

enum class CardStatisticAction {
    CLICK,
    FLIP,
    FAVORITE_ON,
    FAVORITE_OFF,
}