package de.thkoeln.fherborn.fearlesschange.db.initializer

import android.content.Context
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.db.CardKeyword

class CardKeywordInitializer(val context: Context) {
    fun onInsert() {
        CardDatabase.getInstance(context).cardKeywordDao().insertCardKeywords(listOf(
                CardKeyword(0, "Keyword 1"),
                CardKeyword(0, "Keyword 2"),
                CardKeyword(1, "Keyword 2")
        ))
    }
}