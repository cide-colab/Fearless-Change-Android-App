package de.thkoeln.fherborn.fearlesschange.databases.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "keyword")
data class Keyword (

        @PrimaryKey(autoGenerate = true)
        val id: Long,

        val keyword: String
)