package de.thkoeln.fherborn.fearlesschange.persistance.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by florianherborn on 30.07.18.
 */
@Entity(tableName = "card")
data class Card (
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val title: String,
        val pictureName: String,
        val problem: String,
        val buts: String,
        val solution: String,
        var favorite: Boolean = false
)