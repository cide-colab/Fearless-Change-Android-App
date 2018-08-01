package de.thkoeln.fherborn.fearlesschange

import android.app.Application
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase

class App: Application() {

    val cardDB: CardDatabase by lazy {
        CardDatabase.getInstance(applicationContext)
    }

}