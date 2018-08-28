package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.pattern

import android.arch.persistence.room.Embedded

/**
 * Created by florianherborn on 22.08.18.
 */
class PatternInfo (
        @Embedded
        val pattern: Pattern,
        val noteCount: Int
)