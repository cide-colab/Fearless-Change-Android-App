package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.db.CardKeyword

class CardKeywordInitializer: DataInitializer<CardKeyword>("card_keyword") {

    override fun getItemValues(item: CardKeyword?) = hashMapOf(
        "card_id" to item?.card_id,
        "keyword" to item?.keyword
    )

    override fun getItems() = listOf(
            CardKeyword(0, "Keyword 1"),
            CardKeyword(0, "Keyword 2"),
            CardKeyword(1, "Keyword 2")
    )
}