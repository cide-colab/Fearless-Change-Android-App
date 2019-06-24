package de.thkoeln.colab.fearlesschange.data.persistance.pattern

import androidx.room.Embedded

/**
 * Created by florianherborn on 22.08.18.
 */
data class PatternInfo(
        @Embedded
        val pattern: Pattern,
        val noteCount: Int
)