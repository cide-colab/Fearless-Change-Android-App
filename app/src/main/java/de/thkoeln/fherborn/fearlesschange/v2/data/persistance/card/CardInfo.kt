package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.card

import android.arch.persistence.room.Embedded

/**
 * Created by florianherborn on 22.08.18.
 */
class CardInfo (
        @Embedded
        val card: Card,
        val noteCount: Int
)