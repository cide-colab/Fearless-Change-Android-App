package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.db.Keyword

class KeywordInitializer(val context: Context): DataInitializer<Keyword>("keyword") {
    override fun getItemValues(item: Keyword) = listOf(
            item.keyword
    )

    override fun getItems() = listOf(
            Keyword("Keyword 1"),
            Keyword("Keyword 2"),
            Keyword("Keyword 3")
    )
}