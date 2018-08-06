package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.db.CardKeyword

class CardKeywordInitializer(val context: Context): DataInitializer<CardKeyword>("card_keyword") {

    override fun getItemValues(item: CardKeyword) = listOf(
            item.card_id,
            item.keyword
    )

    override fun getItems() = listOf(
            CardKeyword(0, "Keyword 1"),
            CardKeyword(0, "Keyword 2"),
            CardKeyword(1, "Keyword 2")
    )
}