package de.thkoeln.fherborn.fearlesschange.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.databases.repositories.CardRepository

class CardDetailActivity : AppCompatActivity() {

    private lateinit var cardRepository: CardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        val id = getIdFromIntent()

        cardRepository = CardRepository(application)
        cardRepository.getById(id).observe(this, Observer {
            //TODO Set values
        })
    }

    private fun getIdFromIntent(): Long {
        if(!intent.hasExtra(CARD_ID_KEY)) throw IllegalArgumentException("Missing card id in intent")
        return intent.getLongExtra(CARD_ID_KEY, -1)
    }

    companion object {
        const val CARD_ID_KEY = "card_id"
    }

}
