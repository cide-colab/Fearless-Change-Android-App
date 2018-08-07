package de.thkoeln.fherborn.fearlesschange.persistance.initializer

import de.thkoeln.fherborn.fearlesschange.persistance.models.CardKeyword

class CardKeywordInitializer: DataInitializer<CardKeyword>("card_keyword") {

    override fun getItemValues(item: CardKeyword?) = hashMapOf<String, Any?>(
        "cardId" to item?.cardId,
        "keywordId" to item?.keywordId
    )

    fun getValues() = hashMapOf(
            0 to listOf<Int>(),
            1 to listOf<Int>()
    )

    override fun getItems() = getValues().flatMap { cardKeywordList -> cardKeywordList.value.map { CardKeyword(cardKeywordList.key.toLong(), it.toLong()) } }
}