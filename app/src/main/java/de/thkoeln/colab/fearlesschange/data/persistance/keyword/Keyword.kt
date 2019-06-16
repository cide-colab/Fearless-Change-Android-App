package de.thkoeln.colab.fearlesschange.data.persistance.keyword

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "keyword", indices = [Index("id")])
data class Keyword(
        @PrimaryKey(autoGenerate = false)
        val id: Long,
        val keyword: String
) {
    override fun toString(): String = keyword
}