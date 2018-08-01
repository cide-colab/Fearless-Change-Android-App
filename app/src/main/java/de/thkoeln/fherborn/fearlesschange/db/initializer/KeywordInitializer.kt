package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.db.Card
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.db.CardKeyword
import de.thkoeln.fherborn.fearlesschange.db.Keyword
import de.thkoeln.fherborn.fearlesschange.db.initializer.DataInitializer

class KeywordInitializer(val context: Context): DataInitializer {
    override fun onInsert() {
        CardDatabase.getInstance(context).keywordDao().insertKeywords(listOf(
                Keyword("Keyword 1"),
                Keyword("Keyword 2"),
                Keyword("Keyword 3")
        ))
    }
}