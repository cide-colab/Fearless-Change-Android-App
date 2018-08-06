package de.thkoeln.fherborn.fearlesschange.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.databases.models.Card
import de.thkoeln.fherborn.fearlesschange.views.cardview.CardViewSmall


class CardRecyclerGridAdapter(var cards: List<Card> = listOf()) : RecyclerView.Adapter<CardRecyclerGridAdapter.CardViewHolder>() {

    var onCardClickedListener: ((Card, View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardRecyclerGridAdapter.CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_card_grid_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardRecyclerGridAdapter.CardViewHolder, position: Int) {
        holder.bindCard(cards[position])
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var cardView: CardViewSmall = itemView.findViewById(R.id.overview_grid_card)

        fun bindCard(card: Card) {
            cardView.card = card
            cardView.onCardClickedListener = { view, clickedCard ->
                clickedCard?.let { onCardClickedListener?.invoke(it, itemView) }
            }
        }
    }
}