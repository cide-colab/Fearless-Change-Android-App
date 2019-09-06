package de.thkoeln.colab.fearlesschange.persistance.label

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "labels", indices = [Index("id"), Index("name", unique = true)])
data class Label(
        val name: String,
        val color: Int,
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0
)