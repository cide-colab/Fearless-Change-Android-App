package de.thkoeln.colab.fearlesschange.persistance.statistic

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import java.util.*

/**
 * Created by florianherborn on 09.08.18.
 */
@Entity(tableName = "statistic",
        indices = [Index("patternId")],
        foreignKeys = [(ForeignKey(
                entity = Pattern::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("patternId"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ))]
)
data class Statistic (
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,
        val patternId: Long,
        val action: StatisticAction,
        val timestamp: Date = Date()
)