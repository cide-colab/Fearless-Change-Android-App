package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.db.Keyword

class KeywordInitializer(val context: Context) {
    fun onInsert() {
        CardDatabase.getInstance(context).keywordDao().insertKeywords(listOf(
                Keyword("Keyword 1"),
                Keyword("Keyword 2"),
                Keyword("Keyword 3")
        ))
    }
}