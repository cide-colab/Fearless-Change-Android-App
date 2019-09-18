package de.thkoeln.colab.fearlesschange.persistance.pattern

import androidx.room.Embedded

/**
 * Created by florianherborn on 22.08.18.
 */
data class PatternPreviewData(
        @Embedded
        val pattern: Pattern,
        val noteCount: Int
)