package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.db.Card
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.db.CardKeyword
import de.thkoeln.fherborn.fearlesschange.db.Keyword
import de.thkoeln.fherborn.fearlesschange.db.initializer.DataInitializer

class CardKeywordInitializer(val context: Context): DataInitializer {
    override fun onInsert() {
        CardDatabase.getInstance(context).cardKeywordDao().insertCardKeywords(listOf(
                CardKeyword(0, "Keyword 1"),
                CardKeyword(0, "Keyword 2"),
                CardKeyword(1, "Keyword 2")
        ))
    }
}