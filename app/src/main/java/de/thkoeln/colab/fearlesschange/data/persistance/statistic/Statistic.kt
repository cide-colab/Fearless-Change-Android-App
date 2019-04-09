package de.thkoeln.colab.fearlesschange.data.persistance.statistic

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.Pattern
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