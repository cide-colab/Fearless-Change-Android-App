package de.thkoeln.fherborn.fearlesschange.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "keyword")
data class Keyword (
        @PrimaryKey(autoGenerate = false)
        val keyword: String
)