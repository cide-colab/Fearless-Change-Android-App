package de.thkoeln.fherborn.fearlesschange.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.adapters.CardRecyclerGridAdapter
import de.thkoeln.fherborn.fearlesschange.db.CardData
import de.thkoeln.fherborn.fearlesschange.views.cardpopup.CardPopup
import kotlinx.android.synthetic.main.activity_overview.*


class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_overview)
        //TODO PLACEHOLDER START ----
        val cards = CardData.CARDS
        //TODO PLACEHOLDER END ----


        val adapter = CardRecyclerGridAdapter(cards)
        adapter.onCardClickedListener = { card, itemView ->
            CardPopup(itemView.context, card).show()
        }
        overview_recycler_view.adapter = adapter

    }
}
