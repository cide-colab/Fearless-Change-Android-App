package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.CardRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import de.thkoeln.fherborn.fearlesschange.ui.views.cardpopup.CardPopup
import kotlinx.android.synthetic.main.activity_overview.*


class OverviewActivity : AppCompatActivity() {

    private lateinit var cardRepository: CardRepository
    private val adapter = CardRecyclerGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        cardRepository = CardRepository(application)

        overview_recycler_view.adapter = adapter.apply {
            onCardClickedListener = { card, itemView ->
                CardPopup(itemView.context, card).show()
            }
        }

        cardRepository.getAll().observe(this, Observer { cards ->
            adapter.cards = cards?: listOf()
            adapter.notifyDataSetChanged()
        })
    }
}
