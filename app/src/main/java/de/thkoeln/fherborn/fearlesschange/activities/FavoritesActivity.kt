package de.thkoeln.fherborn.fearlesschange.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import de.thkoeln.fherborn.fearlesschange.App
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.CardRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.db.Card
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import de.thkoeln.fherborn.fearlesschange.views.cardpopup.CardPopup
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        loadCards()
    }

    private fun loadCards() {
        //TODO Load only favorites
        (application as App).cardDB.cardDao().getAll().subscribeBy(
                onNext = { addCardsToLayout(it) },
                onError = { Snackbar.make(container, it.localizedMessage, Snackbar.LENGTH_LONG) }
        )
    }

    //TODO Update favorites when favorites button is clicked

    private fun addCardsToLayout(cards: List<Card>) {
        favorites_recycler_view.adapter = CardRecyclerGridAdapter(cards).apply {
            onCardClickedListener = { card, itemView ->
                CardPopup(itemView.context, card).show()
            }
        }
    }
}
