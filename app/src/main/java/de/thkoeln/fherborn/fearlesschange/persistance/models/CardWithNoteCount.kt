package de.thkoeln.fherborn.fearlesschange.persistance.models

import android.arch.persistence.room.Embedded

/**
 * Created by Florian on 19.08.2018.
 */
class CardWithNoteCount(
        @Embedded
        val card: Card,

        val noteCount: Int
)