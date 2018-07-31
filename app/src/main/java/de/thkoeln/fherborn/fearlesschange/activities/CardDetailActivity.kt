package de.thkoeln.fherborn.fearlesschange.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.db.Card

class CardDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
    }

    companion object {
        const val CARD_ID_KEY = "card_id"
    }

}
