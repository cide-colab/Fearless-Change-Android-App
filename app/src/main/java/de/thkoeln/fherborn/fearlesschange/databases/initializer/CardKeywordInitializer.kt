package de.thkoeln.fherborn.fearlesschange.databases.initializer

import de.thkoeln.fherborn.fearlesschange.databases.models.CardKeyword

class CardKeywordInitializer: DataInitializer<CardKeyword>("card_keyword") {

    override fun getItemValues(item: CardKeyword?) = hashMapOf<String, Any?>(
        "card_id" to item?.card_id,
        "keyword_id" to item?.keyword_id
    )

    fun getValues() = hashMapOf(
            0 to listOf<Int>(),
            1 to listOf<Int>()
    )

    override fun getItems() = getValues().flatMap { cardKeywordList -> cardKeywordList.value.map { CardKeyword(cardKeywordList.key.toLong(), it.toLong()) } }
}