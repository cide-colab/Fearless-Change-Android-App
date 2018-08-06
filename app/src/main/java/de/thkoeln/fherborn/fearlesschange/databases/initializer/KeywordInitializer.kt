package de.thkoeln.fherborn.fearlesschange.databases.initializer

import de.thkoeln.fherborn.fearlesschange.databases.models.Keyword

class KeywordInitializer: DataInitializer<Keyword>("keyword") {

    override fun getItemValues(item: Keyword?) = hashMapOf<String, Any?>(
            "keyword" to item?.keyword
    )

    override fun getItems() = listOf(
            Keyword(0,"Keyword 1"),
            Keyword(1,"Keyword 2"),
            Keyword(2,"Keyword 3")
    )
}