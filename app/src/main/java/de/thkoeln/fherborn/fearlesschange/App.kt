package de.thkoeln.fherborn.fearlesschange

import android.app.Application
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase

class App: Application() {

    lateinit var cardDB: CardDatabase

    override fun onCreate() {
        super.onCreate()
        cardDB = CardDatabase.getInstance(applicationContext)
    }
//            by lazy {
//        CardDatabase.getInstance(applicationContext)
//    }

}