package de.thkoeln.fherborn.fearlesschange.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import de.thkoeln.fherborn.fearlesschange.App
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.db.Card
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.db.extensions.loadInBackground
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_overview.*

class CardDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        val id = getIdFromIntent()

        loadInBackground({it.cardDB.cardDao().getById(id)}) {
            onCardLoaded(it)
        }
    }

    private fun getIdFromIntent(): Long {
        if(!intent.hasExtra(CARD_ID_KEY)) throw IllegalArgumentException("Missing card id in intent")
        return intent.getLongExtra(CARD_ID_KEY, -1)
    }

    private fun onCardLoaded(card: Card) {
        //TODO Set values
    }

    companion object {
        const val CARD_ID_KEY = "card_id"
    }

}
