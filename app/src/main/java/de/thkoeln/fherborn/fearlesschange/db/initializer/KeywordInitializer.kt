package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.db.Keyword

class KeywordInitializer: DataInitializer<Keyword>("keyword") {

    override fun getItemValues(item: Keyword?) = hashMapOf<String, Any?>(
            "keyword" to item?.keyword
    )

    override fun getItems() = listOf(
            Keyword("Keyword 1"),
            Keyword("Keyword 2"),
            Keyword("Keyword 3")
    )
}